package zingg.common.core.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.Test;

import zingg.common.client.Arguments;
import zingg.common.client.FieldDefinition;
import zingg.common.client.MatchType;
import zingg.common.client.ZinggClientException;
import zingg.common.client.util.ColName;
import zingg.spark.client.SparkFrame;
import zingg.spark.core.executor.ZinggSparkTester;

public class TestDSUtil extends ZinggSparkTester{
	public static final Log LOG = LogFactory.getLog(TestDSUtil.class);

	@Test
	public void testGetFieldDefColumnsWhenShowConciseIsTrue() throws ZinggClientException {
		
		FieldDefinition def1 = new FieldDefinition();
		def1.setFieldName("field_fuzzy");
		def1.setDataType("string");
		def1.setMatchTypeInternal(MatchType.FUZZY);
		def1.setFields("field_fuzzy");

		FieldDefinition def2 = new FieldDefinition();
		def2.setFieldName("field_match_type_DONT_USE");
		def2.setDataType("string");
		def2.setMatchTypeInternal(MatchType.DONT_USE);
		def2.setFields("field_match_type_DONT_USE");

		FieldDefinition def3 = new FieldDefinition();
		def3.setFieldName("field_str_DONTspaceUSE");
		def3.setDataType("string");
		def3.setMatchTypeInternal(MatchType.getMatchType("DONT_USE"));
		def3.setFields("field_str_DONTspaceUSE");

		List<FieldDefinition> fieldDef = new ArrayList<FieldDefinition>();
		fieldDef.add(def1);
		fieldDef.add(def2);
		fieldDef.add(def3);
		Arguments args = null; 
		try {
			args = new Arguments();
			args.setFieldDefinition(fieldDef);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		StructType schema = DataTypes.createStructType(new StructField[] { 
			DataTypes.createStructField(def1.getFieldName(), DataType.fromDDL(def1.getDataType()), false), 
			DataTypes.createStructField(def2.getFieldName(), DataType.fromDDL(def2.getDataType()), false),
			DataTypes.createStructField(def3.getFieldName(), DataType.fromDDL(def3.getDataType()), false),
			DataTypes.createStructField(ColName.SOURCE_COL, DataTypes.StringType, false) 
		});
		List<Row> list = Arrays.asList(RowFactory.create("1", "first", "one", "Junit"), RowFactory.create("2", "second", "two", "Junit"), 
				RowFactory.create("3", "third", "three", "Junit"), RowFactory.create("4", "forth", "Four", "Junit"));
		Dataset<Row> ds = spark.createDataFrame(list, schema);

		List<String> expectedColumns = new ArrayList<String>();
		expectedColumns.add("field_fuzzy");
		expectedColumns.add(ColName.SOURCE_COL);
		List<Column> colList = zsCTX.getDSUtil().getFieldDefColumns(new SparkFrame(ds), args, false, true);
		assertTrue(expectedColumns.size() == colList.size());
		for (int i = 0; i < expectedColumns.size(); i++) {
			assertTrue(expectedColumns.get(i).equals(colList.get(i).toString()));
		};
	}

	@Test
	public void testGetFieldDefColumnsWhenShowConciseIsFalse() throws ZinggClientException {
		FieldDefinition def1 = new FieldDefinition();
		def1.setFieldName("field_fuzzy");
		def1.setDataType("string");
		def1.setMatchTypeInternal(MatchType.FUZZY);
		def1.setFields("field_fuzzy");

		FieldDefinition def2 = new FieldDefinition();
		def2.setFieldName("field_match_type_DONT_USE");
		def2.setDataType("string");
		def2.setMatchTypeInternal(MatchType.DONT_USE);
		def2.setFields("field_match_type_DONT_USE");

		FieldDefinition def3 = new FieldDefinition();
		def3.setFieldName("field_str_DONTspaceUSE");
		def3.setDataType("string");
		def3.setMatchTypeInternal(MatchType.getMatchType("DONT_USE"));
		def3.setFields("field_str_DONTspaceUSE");

		List<FieldDefinition> fieldDef = new ArrayList<FieldDefinition>();
		fieldDef.add(def1);
		fieldDef.add(def2);
		fieldDef.add(def3);
		Arguments args = null; 
		try {
			args = new Arguments();
			args.setFieldDefinition(fieldDef);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		StructType schema = DataTypes.createStructType(new StructField[] { 
			DataTypes.createStructField(def1.getFieldName(), DataType.fromDDL(def1.getDataType()), false), 
			DataTypes.createStructField(def2.getFieldName(), DataType.fromDDL(def2.getDataType()), false),
			DataTypes.createStructField(def3.getFieldName(), DataType.fromDDL(def3.getDataType()), false),
			DataTypes.createStructField(ColName.SOURCE_COL, DataTypes.StringType, false) 
		});
		List<Row> list = Arrays.asList(RowFactory.create("1", "first", "one", "Junit"), RowFactory.create("2", "second", "two", "Junit"), 
				RowFactory.create("3", "third", "three", "Junit"), RowFactory.create("4", "forth", "Four", "Junit"));
		Dataset<Row> ds = spark.createDataFrame(list, schema);

		List<Column> colListTest2 = zsCTX.getDSUtil().getFieldDefColumns (new SparkFrame(ds), args, false, false);
		List<String> expectedColumnsTest2 = new ArrayList<String>();
		expectedColumnsTest2.add("field_fuzzy");
		expectedColumnsTest2.add("field_match_type_DONT_USE");
		expectedColumnsTest2.add("field_str_DONTspaceUSE");
		expectedColumnsTest2.add(ColName.SOURCE_COL);

		assertTrue(expectedColumnsTest2.size() == colListTest2.size());
		for (int i = 0; i < expectedColumnsTest2.size(); i++) {
			assertTrue(expectedColumnsTest2.get(i).contains(colListTest2.get(i).toString()));
		};
	}
}
