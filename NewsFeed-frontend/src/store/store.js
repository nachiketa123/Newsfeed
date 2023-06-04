import { configureStore } from "@reduxjs/toolkit";
import newsfeedReducer from "./newsfeed-slice";
import userReducer from "./user-slice";
import userFollowReducer from "./user-follow-slice";
import notificationReducer from "./notification-slice";
import jwt_decode from 'jwt-decode';



const store = configureStore({
    reducer: {
        newsfeed: newsfeedReducer,
        user: userReducer,
        userFollow: userFollowReducer,
        notification: notificationReducer,
    }
})

let prevState = store.getState().user.token;

store.subscribe(()=>{
    let token = store.getState().user.token;
    if(prevState !== token){
        try{
        
            let data = jwt_decode(token)
            if (data.exp * 1000 < new Date().getTime()) {
                
                localStorage.removeItem('token');
                window.alert('Session expired, re-login to continue');
                store.dispatch({
                    type: 'RESET_ALL',
                })
            }
        }catch(err){
            console.log(err)
        }
    }
    
})

export default store;