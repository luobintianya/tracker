package com.geek.atracker.core;

import com.geek.atracker.core.enums.Action;
import com.geek.atracker.core.enums.Level;
import com.geek.atracker.core.enums.Model;


public interface AtrackerMaster  {

 public void trackerInfo(Model model,Action action,Level level,Object info );
 
 public void trackerInfo(Model model,Action action,Object info);
 
 public void trackerInfo(Model model,Object info);
 
 public void trackerInfo(Object info);
 
 
 public AtrackerContext getCurrentAtrackerContext();
   
 void setPreAtrackerMaster(AtrackerMaster  pre);
 
 AtrackerMaster getPreAtrackerMaster();
 
}
