package com.geek.atracker.persistence.data.impl;

import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.persistence.data.TransformDataAbs;

public class TransformToStringData  extends TransformDataAbs<String,BaseInfo> {   


	

	@Override
	protected String convertInternal(BaseInfo obj) { 
		return obj.getUserUId();
	}
	
		 
}
