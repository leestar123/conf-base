package com.conf.template.process;

import java.io.File;

import org.conf.application.FileProcess;
import org.conf.application.dto.QualityDataDto;
import org.conf.application.dto.QualityResultDto;

public class DefaultFileProcessImpl extends FileProcess<QualityDataDto, QualityResultDto>{

	@Override
	public QualityDataDto lineParase(String line) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QualityResultDto lineProcess(Integer lineNum, QualityDataDto t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File readData(String... filekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writeData(QualityResultDto b) {
		// TODO Auto-generated method stub
		return null;
	}

}
