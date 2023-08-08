package kr.co.flower.blooming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kr.co.flower.blooming.pdf.PdfGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class FlowerApplication implements ApplicationRunner{

	@Autowired
	private PdfGenerator pdfGenerator;
	
	public static void main(String[] args) {
		SpringApplication.run(FlowerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("generate:!!!!!");
		pdfGenerator.generate();
	}

}
