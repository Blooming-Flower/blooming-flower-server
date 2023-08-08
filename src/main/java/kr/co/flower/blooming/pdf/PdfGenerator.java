package kr.co.flower.blooming.pdf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

@Component
public class PdfGenerator {

	public void generate() {
		String fileName = "test.pdf";
		Document document = new Document(PageSize.A4, 50, 50, 50, 50); // 용지 및 여백 설정

		try {
			// 현재 상대 경로에 fileName.pdf 생성
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

			writer.setInitialLeading(12.5f);

			// pdf 파일 열기
			document.open();
			
			XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
			
			  // 사용할 CSS 를 준비한다.
            CSSResolver cssResolver = new StyleAttrCSSResolver();
            CssFile cssFile = null;
//            try {
//                cssFile = helper.getCSS(new FileInputStream("pdf.css"));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            cssResolver.addCss(cssFile);

            // HTML 과 폰트준비
//            XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
//            fontProvider.register("MALGUN.ttf","MalgunGothic"); // MalgunGothic 은 alias,
//            CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
            CssAppliers cssAppliers = new CssAppliersImpl();

            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            XMLWorker worker = new XMLWorker(css, true);
            XMLParser xmlParser = new XMLParser(worker, Charset.forName("UTF-8"));

            // 폰트 설정에서 별칭으로 줬던 "MalgunGothic"을 html 안에 폰트로 지정한다.
            String htmlStr = "<html><head><body style='font-family: MalgunGothic;'>"
                    + "<p>itextpdf 라이브러리를 사용해 pdf 파일을 만들어 봅니다.</p>"
                    + "<p>1. pdf 파일을 만들고</p>"
                    + "<p>2. css도 붙이고</p>"
                    + "<p>3. html을 만들어서 pdf에 쓰는 형태입니다.</p>"
                    + "</body></head></html>";

            StringReader strReader = new StringReader(htmlStr);
            try {
				xmlParser.parse(strReader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            document.close();
            writer.close();
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}

	}
}
