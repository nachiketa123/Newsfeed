import { createSlice } from "@reduxjs/toolkit";
import resetAllAction from "./reset-all-action";

const initialState = {
    status:'',
    message:'',
}

const notificationSlice = createSlice({
    name:'notification',
    initialState,
    reducers:{
        updatePendingStatus(state){
            state.status = "PENDING";
            state.message = "Data loading please wait..";
        },
        updateSuccessStatus(state){
            state.status = "SUCCESS";
            state.message = "SUCCESSFUL";
        },
        updateFailureStatus(state,action){
            state.status = "FAILURE";
            state.message = action.payload;
        },
        resetStatus(state){
            state.status = "";
            state.message = "";
        },
        // updateState(state,action){
        //     const { status, message} = action.payload;
        //     if(status === "PENDING")
        //         message="Data loading please wait.."
        //     else
        //     if(status === "SUCCESS")
        //         message="SUCCESS"
        //     else
        //     if(status !== "FAILURE")
        //         message = "UNKNOWN State"
            
        //     state.status = status;
        //     state.message = message;
        // }
    },
    extraReducers:(builder)=>{ builder.addCase(resetAllAction, ()=>initialState)}
})

export const notificationActions = notificationSlice.actions;

export default notificationSlice.reducer;