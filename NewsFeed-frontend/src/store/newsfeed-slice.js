import { createSlice } from "@reduxjs/toolkit";
import resetAllAction from "./reset-all-action";

const initialState = {
    feedItems : []
}

const newsfeedSlice = createSlice({
    name:'newsfeed',
    initialState, 
    reducers:{
        setFeedItem(state,action){
            state.feedItems = action.payload
        },
        addFeedItem(state,action){
           state.feedItems = [action.payload, ...state.feedItems]
        },
        updateUpvoteDownvoteOnFeedItemAt(state,action){
            const {index, upvote, downvote, isLikedByUser, isDislikedByUser} = action.payload
            if(state.feedItems[index]){
                state.feedItems[index].feedItem.upvote = upvote;
                state.feedItems[index].feedItem.downvote = downvote;
                state.feedItems[index].isLikedByUser = isLikedByUser
                state.feedItems[index].isDislikedByUser = isDislikedByUser
            }
            
        },
        updateUpvoteDownvoteOnCommentAt(state,action){
            // destructuring
            const {feedItemIndex, 
                commentIndex, 
                upvote, 
                downvote, 
                isLikedByTheUser, 
                isDislikedByTheUser} = action.payload

            if(state.feedItems[feedItemIndex] && state.feedItems[feedItemIndex].feedItem.comment[commentIndex]){
                state.feedItems[feedItemIndex].feedItem.comment[commentIndex].upvote = upvote;
                state.feedItems[feedItemIndex].feedItem.comment[commentIndex].downvote = downvote;
                state.feedItems[feedItemIndex].feedItem.comment[commentIndex].isLikedByTheUser = isLikedByTheUser
                state.feedItems[feedItemIndex].feedItem.comment[commentIndex].isDislikedByTheUser = isDislikedByTheUser
            }
            
        },
        addCommentOnFeedAt(state,action){
            const {index, comment} = action.payload;
            let newCommentArr = state.feedItems[index].feedItem.comment.slice();
            newCommentArr = [comment, ...newCommentArr];
            state.feedItems[index].feedItem.comment = newCommentArr;
         },
         


    },
    extraReducers: (builder)=>builder.addCase(resetAllAction,()=>initialState)
}) 

export const newsfeedSliceActions = newsfeedSlice.actions;

export default newsfeedSlice.reducer;