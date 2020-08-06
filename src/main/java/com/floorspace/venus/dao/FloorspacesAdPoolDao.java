package com.floorspace.venus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.floorspace.venus.enums.City;
import com.floorspace.venus.enums.FloorSpaceConstructionType;
import com.floorspace.venus.enums.LatLon;
import com.floorspace.venus.enums.PropertyType;
import com.floorspace.venus.pojo.AdPoolPayload;
import com.floorspace.venus.pojo.ApartmentConstructionAdPoolDetails;
import com.floorspace.venus.pojo.CommonAdPoolDetails;
import com.floorspace.venus.pojo.HouseConstructionAdPoolDetails;
import com.floorspace.venus.pojo.LenderBuyerMapping;
import com.floorspace.venus.pojo.PIIUserInfo;
import com.floorspace.venus.pojo.StandaloneBuildingConstructionAdPoolDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class FloorspacesAdPoolDao {
	
	 private JdbcTemplate jdbcTemplate;
	 
	 public FloorspacesAdPoolDao(DataSource dataSource){
		 jdbcTemplate = new JdbcTemplate(dataSource);
	 }
	 private final static String JOIN_ALL_AD_POOL_TABLES = "select * from common_ad_info INNER JOIN house_construction_ad_info ON common_ad_info.pool_id  = house_construction_ad_info.pool_id INNER JOIN apartment_ad_info ON common_ad_info.pool_id  = apartment_ad_info.pool_id  INNER JOIN standalone_building_ad_info ON common_ad_info.pool_id  = standalone_building_ad_info.pool_id";

//	 private final static String JOIN_ALL_AD_POOL_TABLES = "select * from common_ad_info JOIN house_construction_ad_info JOIN standalone_building_ad_info JOIN"
//	 		+ " apartment_ad_info";
	 
	 private final static String GET_ALL_ADS_FOR_A_LENDER = JOIN_ALL_AD_POOL_TABLES + " where lender_id=?;";
	 
	 private final static String GET_AD_WITH_ID = JOIN_ALL_AD_POOL_TABLES + " where common_ad_info.pool_id=?;";
	 
	 private final static String GET_ADS_BASED_ON_FILTERS = JOIN_ALL_AD_POOL_TABLES + " where city =? and property_type=?"
	 		+ " and construction_type=? and property_cost>=? and property_cost<=?";
	 
	 private final static String INSERT_COMMON_DETAILS = "INSERT INTO common_ad_info VALUES (?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
	 					+ "	property_type=?, construction_type=?, city=?, latitude=?, longitude=?, address=?, description=?, property_cost=?, num_of_pool_members=? ";
	 
	 private final static String INSERT_HOUSE_CONSTRUCTION_DETAILS = "INSERT INTO house_construction_ad_info VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
	 					+ "existing_num_of_floors=?, floorspace_area=?,floorspace_length=?,floorspace_breadth=?,num_Of_allowed_floors=? ";
	 
	 private final static String INSERT_BUILDING_CONSTRUCTION_DETAILS = "INSERT INTO standalone_building_ad_info VALUES (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
			 			+ "num_of_building_floors=?, building_area=?,building_length=?,building_breadth=?,num_Of_allowed_building_floors=? ";
	 
	 private final static String INSERT_APARTMENT_CONSTRUCTION_DETAILS = "INSERT INTO apartment_ad_info VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE "
	 					+ "num_of_apartment_floors=?,num_of_flats_per_floor=?, apartment_area=?,apartment_length=?,apartment_breadth=?,num_Of_allowed_apartment_floors=? ";
	  
	 private final static String INSERT_OR_UPDATE_LENDER_BUYER_MAPPING_REQUEST = "INSERT INTO lender_buyer_pool_mapping VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE "
				+ " requested=? ";
	 
	 private final static String INSERT_OR_UPDATE_LENDER_BUYER_MAPPING_ACCEPT = "INSERT INTO lender_buyer_pool_mapping VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE "
				+ " accepted=? ";
	 
	 private final static String GET_LENDER_BUYER_MAPPING_FOR_LENDER = "SELECT * FROM lender_buyer_pool_mapping where lender_id=?";
	 
	 private final static String GET_LENDER_BUYER_MAPPING_FOR_BUYER = "SELECT * FROM lender_buyer_pool_mapping where buyer_id=?";

	 
	 public AdPoolPayload getAdBasedOnID(UUID poolID) {
		 List<Object> args = new ArrayList<Object>();
		 args.add(poolID.toString());
		 AdPoolPayload payload  = jdbcTemplate.queryForObject(GET_AD_WITH_ID, args.toArray(), new AdPoolPayloadMapper());
		 return payload;
	 }
	 
	 public List<AdPoolPayload> getAllAdsPostedByLender(UUID lenderID) {
		 List<Object> args = new ArrayList<Object>();
		 args.add(lenderID.toString());
		 List<AdPoolPayload> payloads  = jdbcTemplate.query(GET_ALL_ADS_FOR_A_LENDER, args.toArray(),new AdPoolPayloadMapper());
		 return payloads;
		 
	 }
	 
	 public void createFloorSpaceAd(AdPoolPayload adPoolPayload) throws SQLException {
		 UUID poolID = null;
		 if(adPoolPayload.getCommonDetails().getPoolID()==null) {
			  poolID = UUID.randomUUID();
		 }
		 else {
			  poolID = adPoolPayload.getCommonDetails().getPoolID();
		 }
		 
	     DataSource dataSource = jdbcTemplate.getDataSource();
		 Connection con  = dataSource.getConnection();
		 PreparedStatement commonInsertStatement = null;
		 PreparedStatement houseInsertStatement = null;
		 PreparedStatement buildingInsertStatement = null;
		 PreparedStatement apartmentInsertStatement = null;
		 
		 con.setAutoCommit(false);
		 
		 String commonInsert = INSERT_COMMON_DETAILS;
		 commonInsertStatement = con.prepareStatement(commonInsert);		 
		 setPreparestatementParamsForCommmonDetails(commonInsertStatement, poolID,  adPoolPayload.getCommonDetails());
		 commonInsertStatement.executeUpdate();
		 
		 String houseInsert = null;
		 houseInsert = INSERT_HOUSE_CONSTRUCTION_DETAILS;
		 houseInsertStatement = con.prepareStatement(houseInsert);
		 setPreparestatementParamsForHouseConstructionDetails(houseInsertStatement,poolID, adPoolPayload.getHouseconstructionAdDetails());
		 houseInsertStatement.executeUpdate();
		 
		 String buildingInsert = null;
		 buildingInsert = INSERT_BUILDING_CONSTRUCTION_DETAILS;
		 buildingInsertStatement = con.prepareStatement(buildingInsert);
		 setPreparestatementParamsForBuildingConstructionDetails(buildingInsertStatement,poolID, adPoolPayload.getStandaloneBuildingAdDetails());
		 buildingInsertStatement.executeUpdate();
		 
		 String apartmentInsert = null;
		 apartmentInsert = INSERT_APARTMENT_CONSTRUCTION_DETAILS;
		 apartmentInsertStatement = con.prepareStatement(apartmentInsert);
		 setPreparestatementParamsForApartmentConstructionDetails(apartmentInsertStatement,poolID, adPoolPayload.getApartmentConstructionAdDetails());
		 apartmentInsertStatement.executeUpdate();
		 
         con.commit();
		} 

	private void setPreparestatementParamsForCommmonDetails(PreparedStatement commonInsertStatement, UUID poolID, CommonAdPoolDetails commonDetails) throws SQLException {
		 commonInsertStatement.setString(1, poolID.toString());
		 commonInsertStatement.setString(2, commonDetails.getLenderID().toString());
		 commonInsertStatement.setString(3, commonDetails.getPropertyType().name());
		 commonInsertStatement.setString(4, commonDetails.getConstructionType().name());
		 commonInsertStatement.setString(5, commonDetails.getCity().name());
		 commonInsertStatement.setDouble(6, commonDetails.getLatlon().getLatitude());
		 commonInsertStatement.setDouble(7, commonDetails.getLatlon().getLongitude());
		 commonInsertStatement.setString(8, commonDetails.getAddress());
		 commonInsertStatement.setString(9, commonDetails.getDescription());
		 commonInsertStatement.setInt(10, commonDetails.getPropertyCost());
		 commonInsertStatement.setInt(11, commonDetails.getNumOfPoolMembers());
		 //For update
		 commonInsertStatement.setString(12, commonDetails.getPropertyType().name());
		 commonInsertStatement.setString(13, commonDetails.getConstructionType().name());
		 commonInsertStatement.setString(14, commonDetails.getCity().name());
		 commonInsertStatement.setDouble(15, commonDetails.getLatlon().getLatitude());
		 commonInsertStatement.setDouble(16, commonDetails.getLatlon().getLongitude());
		 commonInsertStatement.setString(17, commonDetails.getAddress());
		 commonInsertStatement.setString(18, commonDetails.getDescription());
		 commonInsertStatement.setInt(19, commonDetails.getPropertyCost());
		 commonInsertStatement.setInt(20, commonDetails.getNumOfPoolMembers());
	 }
	 
	 private void setPreparestatementParamsForHouseConstructionDetails(PreparedStatement commonInsertStatement, UUID poolID, HouseConstructionAdPoolDetails houseConstructionDetails) throws SQLException {
		 commonInsertStatement.setString(1, poolID.toString());
		 commonInsertStatement.setInt(2, houseConstructionDetails.getExistingFloors());
		 commonInsertStatement.setDouble(3, houseConstructionDetails.getFloorSpaceArea());
		 commonInsertStatement.setDouble(4, houseConstructionDetails.getLengthOfFloorSpace());
		 commonInsertStatement.setDouble(5, houseConstructionDetails.getBreadthOfFloorSpace());
		 commonInsertStatement.setDouble(6, houseConstructionDetails.getNumOfAllowedFloorsForConstruction());
		 
		 commonInsertStatement.setInt(7, houseConstructionDetails.getExistingFloors());
		 commonInsertStatement.setDouble(8, houseConstructionDetails.getFloorSpaceArea());
		 commonInsertStatement.setDouble(9, houseConstructionDetails.getLengthOfFloorSpace());
		 commonInsertStatement.setDouble(10, houseConstructionDetails.getBreadthOfFloorSpace());
		 commonInsertStatement.setDouble(11, houseConstructionDetails.getNumOfAllowedFloorsForConstruction());
	 }
	 
	 private void setPreparestatementParamsForBuildingConstructionDetails(PreparedStatement commonInsertStatement, UUID poolID, StandaloneBuildingConstructionAdPoolDetails standaloneBuildingDetails) throws SQLException {
		 commonInsertStatement.setString(1, poolID.toString());
		 commonInsertStatement.setInt(2, standaloneBuildingDetails.getNumOfFloors());
		 commonInsertStatement.setDouble(3, standaloneBuildingDetails.getPlotArea());
		 commonInsertStatement.setDouble(4, standaloneBuildingDetails.getLengthOfPlot());
		 commonInsertStatement.setDouble(5, standaloneBuildingDetails.getBreadthOfPlot());
		 commonInsertStatement.setInt(6, standaloneBuildingDetails.getNumOfAllowedFloorsForConstruction());
		 
		 commonInsertStatement.setInt(7, standaloneBuildingDetails.getNumOfFloors());
		 commonInsertStatement.setDouble(8, standaloneBuildingDetails.getPlotArea());
		 commonInsertStatement.setDouble(9, standaloneBuildingDetails.getLengthOfPlot());
		 commonInsertStatement.setDouble(10, standaloneBuildingDetails.getBreadthOfPlot());
		 commonInsertStatement.setInt(11, standaloneBuildingDetails.getNumOfAllowedFloorsForConstruction());
	 }
	 
	 private void setPreparestatementParamsForApartmentConstructionDetails(PreparedStatement commonInsertStatement, UUID poolID, ApartmentConstructionAdPoolDetails apartmentDetails) throws SQLException {
		 commonInsertStatement.setString(1, poolID.toString());
		 commonInsertStatement.setInt(2, apartmentDetails.getNumOfApartmentFloors());
		 commonInsertStatement.setInt(3, apartmentDetails.getNumOfFlatsPerFloor());
		 commonInsertStatement.setDouble(4, apartmentDetails.getApartmentArea());
		 commonInsertStatement.setDouble(5, apartmentDetails.getLengthOfApartmentPlot());
		 commonInsertStatement.setDouble(6, apartmentDetails.getBreadthOfApartmentPlot());
		 commonInsertStatement.setInt(7, apartmentDetails.getNumOfAllowedFloorsForApartment());
		 
		 commonInsertStatement.setInt(8, apartmentDetails.getNumOfApartmentFloors());
		 commonInsertStatement.setInt(9, apartmentDetails.getNumOfFlatsPerFloor());
		 commonInsertStatement.setDouble(10, apartmentDetails.getApartmentArea());
		 commonInsertStatement.setDouble(11, apartmentDetails.getLengthOfApartmentPlot());
		 commonInsertStatement.setDouble(12, apartmentDetails.getBreadthOfApartmentPlot());
		 commonInsertStatement.setInt(13, apartmentDetails.getNumOfAllowedFloorsForApartment());
	 }

	public List<AdPoolPayload>  getFiltersBasedFloorspaces(City city, PropertyType propertyType,
			FloorSpaceConstructionType constructionType, Integer maxPrice, Integer minPrice) {
		 List<Object> args = new ArrayList<Object>();
		 args.add(city.name());
		 args.add(propertyType.name());
		 args.add(constructionType.name());
		 args.add(minPrice);
		 args.add(maxPrice);
		 List<AdPoolPayload> payloads  = jdbcTemplate.query(GET_ADS_BASED_ON_FILTERS,args.toArray(),new AdPoolPayloadMapper());
		 return payloads;
	}

	public boolean sendRequestToLenderFromBuyer(UUID poolID, UUID lenderID, UUID buyerID) {
		
		List<Object> args = new ArrayList<Object>();
		args.add(poolID.toString());
		args.add(lenderID.toString());
		args.add(buyerID.toString());
		args.add(true);
		args.add(false);
		args.add(true);
		int updated = jdbcTemplate.update(INSERT_OR_UPDATE_LENDER_BUYER_MAPPING_REQUEST, args.toArray());
		return updated==1;
	}
	
	public boolean acceptRequestFromLenderToBuyer(UUID poolID, UUID lenderID, UUID buyerID) {
			
			List<Object> args = new ArrayList<Object>();
			args.add(poolID.toString());
			args.add(lenderID.toString());
			args.add(buyerID.toString());
			args.add(false);
			args.add(true);
			args.add(true);
			int updated = jdbcTemplate.update(INSERT_OR_UPDATE_LENDER_BUYER_MAPPING_ACCEPT, args.toArray());
			return updated==1;
	}

	public List<LenderBuyerMapping> getConnectedPoolsForALender(UUID lenderID) {
		List<Object> args = new ArrayList<Object>();
		args.add(lenderID.toString());
		List<LenderBuyerMapping> lenderMappings = jdbcTemplate.query(GET_LENDER_BUYER_MAPPING_FOR_LENDER, args.toArray(), new LenderBuyerMappingMapper());
		return lenderMappings;
	}
	
	public List<LenderBuyerMapping> getConnectedPoolsForABuyer(UUID buyerID) {
		List<Object> args = new ArrayList<Object>();
		args.add(buyerID.toString());
		List<LenderBuyerMapping> buyerMappings = jdbcTemplate.query(GET_LENDER_BUYER_MAPPING_FOR_BUYER, args.toArray(), new LenderBuyerMappingMapper());
		return buyerMappings;
	}
}

	 class AdPoolPayloadMapper implements RowMapper<AdPoolPayload>{

			public AdPoolPayload mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommonAdPoolDetails commonDetails = new CommonAdPoolDetails(UUID.fromString(rs.getString("pool_id")), UUID.fromString(rs.getString("lender_id")), 
						PropertyType.valueOf(rs.getString("property_type")), FloorSpaceConstructionType.valueOf(rs.getString("construction_type")), 
						City.valueOf(rs.getString("city")), new LatLon(rs.getDouble("latitude"), rs.getDouble("longitude")), rs.getString("address"),  
								rs.getString("description"), rs.getInt("property_cost"), rs.getInt("num_of_pool_members"));
				
				HouseConstructionAdPoolDetails houseconstructionAdDetails = new HouseConstructionAdPoolDetails(rs.getInt("existing_num_of_floors"), 
						rs.getDouble("floorspace_area"), rs.getDouble("floorspace_length"), rs.getDouble("floorspace_breadth"), rs.getInt("num_Of_allowed_floors"));
				
				StandaloneBuildingConstructionAdPoolDetails standaloneBuildingAdDetails = new StandaloneBuildingConstructionAdPoolDetails(rs.getInt("num_of_building_floors"), 
						rs.getDouble("building_area"), rs.getDouble("building_length"), rs.getDouble("building_breadth"), rs.getInt("num_Of_allowed_building_floors"));
						
				ApartmentConstructionAdPoolDetails apartmentConstructionAdPoolDetails = new ApartmentConstructionAdPoolDetails(rs.getInt("num_of_apartment_floors"), rs.getInt("num_of_flats_per_floor"), 
						rs.getDouble("apartment_area"), rs.getDouble("apartment_length"), rs.getDouble("apartment_breadth"), rs.getInt("num_Of_allowed_apartment_floors"));
				
				AdPoolPayload payload = new AdPoolPayload(commonDetails, houseconstructionAdDetails, standaloneBuildingAdDetails, apartmentConstructionAdPoolDetails);
				return payload;
			}
		}
	 
	 class LenderBuyerMappingMapper implements RowMapper<LenderBuyerMapping>{

		 public LenderBuyerMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
				 LenderBuyerMapping mapping = new LenderBuyerMapping(UUID.fromString(rs.getString("pool_id")), UUID.fromString(rs.getString("lender_id")),
						 UUID.fromString(rs.getString("buyer_id")), rs.getBoolean("requested"), rs.getBoolean("accepted"));
				 return mapping;
			}
		}

