import { createSlice } from "@reduxjs/toolkit";
import resetAllAction from "./reset-all-action";

const initialState = {
    searchResult : []
}

const userFollowSlice = createSlice({
    name:'userFollow',
    initialState,
    reducers:{
        setSearchResult : (state,action)=>{
            state.searchResult = action.payload;
        },
        setIsFollowedByCurrentUser(state,action){
            let index = state.searchResult.findIndex(user => user.email === action.payload.email)
            state.searchResult[index].isFollowedByCurrentUser = true;
        },
        unsetIsFollowedByCurrentUser(state,action){
            let index = state.searchResult.findIndex(user => user.email === action.payload.email)
            state.searchResult[index].isFollowedByCurrentUser = false;
        },

    },
    extraReducers: (builder)=>builder.addCase(resetAllAction,()=>initialState)
})

export const userFollowActions = userFollowSlice.actions;

export default userFollowSlice.reducer