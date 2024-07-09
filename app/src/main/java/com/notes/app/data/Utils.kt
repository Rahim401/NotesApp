package com.notes.app.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class SortNotesBy(val type:Int, val isDescending:Boolean = false){
//    ByIsUnchecked(1, true), ByIsChecked(1),
    ByDate(2), ByDateDescending(2, true),
    ByTitle(3), ByTitleDescending(3, true),
}

val dateFormat = SimpleDateFormat("MMM d yyyy, EEE", Locale.US)
fun sortToDoListBy(msgList: List<Note>, sortBy: SortNotesBy): Pair<List<Note>,Map<Int,String>>{
    val sortedList = when(sortBy){
//        SortNotesBy.ByIsChecked -> msgList.sortedBy { if(it.isDone) 0 else 1 }
//        SortNotesBy.ByIsUnchecked -> msgList.sortedByDescending { if(it.isDone) 0 else 1 }
        SortNotesBy.ByDate -> msgList.sortedBy { it.createdAt }
        SortNotesBy.ByDateDescending -> msgList.sortedByDescending { it.createdAt }
        SortNotesBy.ByTitle -> msgList.sortedBy { it.title }
        SortNotesBy.ByTitleDescending -> msgList.sortedByDescending { it.title }
    }

    val headerMap = HashMap<Int,String>(sortedList.size/4)
//    if(sortBy==SortNotesBy.ByIsChecked){
//        headerMap[0] = "Tasks Completed"
//        headerMap[sortedList.count { it.isDone }] = "UnCompleted Tasks"
//    }
//    else if(sortBy==SortNotesBy.ByIsUnchecked){
//        headerMap[0] = "UnCompleted Tasks"
//        headerMap[sortedList.count { !it.isDone }] = "Tasks Completed"
//    }
//    else
    if(sortBy.type==2) sortedList.forEachIndexed { idx, msg ->
        var msgDate = dateFormat.format(Date(msg.createdAt))
        msgDate = "on $msgDate"
        if(!headerMap.containsValue(msgDate))
            headerMap[idx] = msgDate
    }

    return Pair(sortedList, headerMap)
}