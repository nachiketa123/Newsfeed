import { createSlice } from "@reduxjs/toolkit";
import resetAllAction from "./reset-all-action";

const initialState = {
    user_id:'',
    name:'',
    email:'',
    token:'',
}
const userSlice = createSlice({
    name:'user',
    initialState,
    extraReducers:(builder)=>builder.addCase(resetAllAction,() => initialState),
    reducers:{
        setUser(state,action){
            state.user_id = action.payload.user_id;
            state.name = action.payload.name;
            state.email = action.payload.email;
            state.token = action.payload.token;
        },
    },
    
})

export const userActions = userSlice.actions;

export default  userSlice.reducer;