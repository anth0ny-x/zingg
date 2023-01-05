package zingg.spark.util;
import zingg.client.Arguments;
import zingg.client.FieldDefinition;
import zingg.client.ZFrame;
import zingg.client.ZinggClientException;
import zingg.client.util.ColName;
import zingg.client.util.ColValues;
import zingg.feature.Feature;
import zingg.feature.FeatureFactory;
import zingg.model.Model;
import zingg.spark.feature.SparkFeatureFactory;
import zingg.spark.model.SparkLabelModel;
import zingg.spark.model.SparkModel;
import zingg.util.ModelUtil;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;


public class SparkModelUtil extends ModelUtil<SparkSession,DataType,Dataset<Row>, Row, Column> {

    public static final Log LOG = LogFactory.getLog(SparkModelUtil.class);
    

    public SparkModelUtil(SparkSession s) {
        this.session = s;
    }

	public Model<SparkSession,DataType,Dataset<Row>, Row, Column> getModel(boolean isLabel, Arguments args) throws ZinggClientException{
        Model<SparkSession,DataType,Dataset<Row>, Row, Column> model = null;
        if (isLabel) {
            model = new SparkLabelModel(getFeaturers(args));
        }
        else {
            model = new SparkModel(getFeaturers(args));            
        }
        return model;
    }

    @Override
    public Model<SparkSession,DataType,Dataset<Row>, Row, Column> loadModel(boolean isLabel,
        Arguments args) throws ZinggClientException    {
        Model<SparkSession,DataType,Dataset<Row>, Row, Column> model = getModel(isLabel, args);
        model.load(args.getModel());
        return model;

     }

    @Override
    public FeatureFactory<DataType> getFeatureFactory() {
        return new SparkFeatureFactory();
    }

}
