package com.geek.atracker.persistence.data.impl;

import java.util.HashMap;
import java.util.Map;

import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.persistence.data.TransformDataAbs;

public class TransformToMapData extends TransformDataAbs<Map<String, String>,BaseInfo> {

	@Override
	protected Map<String, String> convertInternal(BaseInfo obj) {
		Map<String,String> values=new HashMap<String, String>();
		values.put("traker", obj.toString());
		return values;
	}

	 

}
