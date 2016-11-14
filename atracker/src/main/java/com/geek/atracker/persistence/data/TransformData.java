package com.geek.atracker.persistence.data;

import com.geek.atracker.data.tracking.BaseInfo;


/**
 * store data bag used transform customer data
 * 
 * @author Robin
 *
 */
public abstract interface TransformData<T,S  extends BaseInfo> {
	
	   T  getContent(S obj);   
}
