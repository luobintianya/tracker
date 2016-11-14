package com.geek.atracker.utils;

import com.geek.atracker.core.enums.Model;
import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.data.tracking.CartBaseInfo;

/** Depend on which kind of customer data and  will Convert object to target BaseInfo sub class
 * @author Robin
 *
 */
public class ObjectToT  {
	
	public static BaseInfo convertObjToCartT(Object t,Model model){
		
		return new CartBaseInfo(t.toString());
	}

	 
}
