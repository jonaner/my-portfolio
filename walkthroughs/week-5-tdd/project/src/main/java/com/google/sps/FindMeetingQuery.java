// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    boolean meetsConditions = true; 
    boolean meetsTerms = true;
    Collection<TimeRange> ret = new ArrayList<TimeRange> ();
    TimeRange [] listOfTimeRange = new TimeRange [events.size()];
    TimeRange fillIn;
    int eventDuration =0;
    int j = 0;
    int start = 0;
    int end = 0; 
    int enoughRoom = 0;
    
    if(events.isEmpty() || (events.isEmpty() && request.getAttendees().isEmpty())){
      meetsConditions = false;
    } if (request.getDuration()> TimeRange.WHOLE_DAY.duration()){
      meetsTerms = false;
    } if (meetsConditions && meetsTerms){
        for(Event temp: events){
          listOfTimeRange [j]= temp.getWhen();
          j++;
        }
        for(int i=0;i<listOfTimeRange.length-1; i++){  
          
          if (listOfTimeRange[0].start() == 0 && listOfTimeRange[listOfTimeRange.length-1].duration()==TimeRange.WHOLE_DAY.duration()){
            ret.add(TimeRange.fromStartDuration(listOfTimeRange[i].duration(),listOfTimeRange[i].start()));
            i = listOfTimeRange.length;
          }
          else{
            
          }
        }
        }
        
    }

    if (!meetsConditions){
        ret.add(TimeRange.WHOLE_DAY);
    }
    if (!meetsTerms){
        ret.clear();
    }
    return ret;
  }
}
