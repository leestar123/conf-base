package com.conf.template.process;

import java.util.List;

import org.conf.application.FileProcess;
import org.conf.application.dto.QualityDataDto;
import org.conf.application.dto.QualityResultDto;

public class DefaultFileProcessImpl extends FileProcess<QualityDataDto, QualityResultDto>{

	@Override
	public QualityResultDto lineProcess(Integer lineNum, QualityDataDto t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTotalNum(String... filekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void combineData(List<String> returnData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<QualityDataDto> readData(Integer startLine, Integer endLine, String... filekey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String writeData(List<QualityResultDto> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCommitInterval() {
		// TODO Auto-generated method stub
		return null;
	}

}
