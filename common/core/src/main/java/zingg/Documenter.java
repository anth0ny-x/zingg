package zingg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zingg.client.ZinggClientException;
import zingg.client.ZinggOptions;
import zingg.documenter.ModelDocumenter;
import zingg.documenter.DataDocumenter;
import zingg.client.ZFrame;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import zingg.client.FieldDefinition;
import zingg.client.MatchType;
import zingg.client.ZinggClientException;
import zingg.client.ZinggOptions;
import zingg.client.util.ColName;
import zingg.util.DSUtil;
import zingg.util.PipeUtilBase;
import zingg.util.RowAdapter;
import zingg.util.RowWrapper;

public abstract class Documenter<S,D,R,C,T> extends ZinggBase<S,D,R,C,T> {

	protected static String name = "zingg.Documenter";
	public static final Log LOG = LogFactory.getLog(Documenter.class);

	public Documenter() {
		setZinggOptions(ZinggOptions.GENERATE_DOCS);
 	}

	public void execute() throws ZinggClientException {
		try {
			LOG.info("Documenter starts");
			//Documentation out of model
			/*ModelDocumenter modelDoc = new ModelDocumenter(args);
			modelDoc.process();

			//Documnetation of data
			DataDocumenter dataDoc = new DataDocumenter(args);
			dataDoc.process();
			*/

			LOG.info("Documenter finishes");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ZinggClientException(e.getMessage());
		}
	}
}
