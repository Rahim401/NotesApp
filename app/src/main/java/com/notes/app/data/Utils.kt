package com.notes.app.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class SortNotesBy(val type:Int, val isDescending:Boolean = false){
//    ByIsUnchecked(1, true), ByIsChecked(1),
    ByTitle(2), ByTitleDescending(2, true),
    ByCreateTime(3), ByCreateTimeDes(3, true),
    ByUpdateTime(4), ByUpdateTimeDes(4, true),
}

val dateFormat = SimpleDateFormat("MMM d yyyy, EEE", Locale.US)
val timeFormat = SimpleDateFormat("MMM d, hh:mm a", Locale.US)
fun sortToDoListBy(msgList: List<Note>, sortBy: SortNotesBy): Pair<List<Note>,Map<Int,String>>{
    val sortedList = when(sortBy){
//        SortNotesBy.ByIsChecked -> msgList.sortedBy { if(it.isDone) 0 else 1 }
//        SortNotesBy.ByIsUnchecked -> msgList.sortedByDescending { if(it.isDone) 0 else 1 }
        SortNotesBy.ByTitle -> msgList.sortedBy { it.title }
        SortNotesBy.ByTitleDescending -> msgList.sortedByDescending { it.title }
        SortNotesBy.ByCreateTime -> msgList.sortedBy { it.createdAt }
        SortNotesBy.ByCreateTimeDes -> msgList.sortedByDescending { it.createdAt }
        SortNotesBy.ByUpdateTime -> msgList.sortedBy { it.updatedAt }
        SortNotesBy.ByUpdateTimeDes -> msgList.sortedByDescending { it.updatedAt }
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
    if(sortBy.type > 2) sortedList.forEachIndexed { idx, note ->
        var noteData = dateFormat.format(
            if(sortBy.type == 3) Date(note.createdAtInMilli)
            else Date(note.updatedAt)
        )

        noteData = if(sortBy.type == 3) "created on $noteData"
        else "updated on $noteData"
        if(!headerMap.containsValue(noteData))
            headerMap[idx] = noteData
    }

    return Pair(sortedList, headerMap)
}