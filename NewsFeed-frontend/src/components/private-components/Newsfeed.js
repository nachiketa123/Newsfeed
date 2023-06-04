import './newsfeed-common.css';

import React, { useEffect, useRef, useState } from 'react';
import Modal from '../common/Modal';
import Comments from './Comments';

import { useSelector, useDispatch } from 'react-redux';
import Feeditem from './Feeditem';
import { getNewsFeedData, upvotePost, downvotePost, addComment } from '../../store/actions/newsfeed-action';
import AddFeeditem from './AddFeeditem';
import ListGroupComponent from '../common/ListGroupComponent';


const NewsFeed = () => {
    
    //selector- redux state
    const { feedItems } = useSelector(state=> state.newsfeed);
    const {  email, user_id } = useSelector(state=> state.user)
    const dispatch = useDispatch();

    //useRef
    const modalRef = useRef();

    //use state
    const [showPostModal, setShowPostModal] = useState(false)
    const [showPostModalIndex, setShowPostModalIndex] = useState(-1);
    const [showCommentModal,setShowCommentModal] = useState(false)

    /*side effect*/

    // To hide the PostModal when clicked outside of the modal
            //To be implemented

    //TO get the news feed
    useEffect(()=>{
        if(email && email.length!==0 )
            dispatch(getNewsFeedData(email))
    },[dispatch,email])

    const closeFullPost = () =>{
        setShowPostModal(false)
    }

    //click handlers
    const handleUpVote = (index,feedItemId) =>{
        dispatch(upvotePost(email,index,feedItemId))
    }

    const handleDownVote = (index,feedItemId) =>{
        dispatch(downvotePost(email,index,feedItemId))
    }

    const showFullPost = (index) =>{
        setShowPostModal(true)
        setShowPostModalIndex(index);
    }

    // After submitting the comment 
    const handleOnCommentAdd = (e,commentText,feeditem_id) =>{
        e.preventDefault();
        dispatch(addComment(email,showPostModalIndex,feeditem_id,commentText,feeditem_id))
    }   

    const openCommentModal = (index) =>{
        setShowPostModal(false)
        setShowCommentModal(true)
        setShowPostModalIndex(index)
    }
    const closeCommentModal = ()=>{
        setShowCommentModal(false);
        setShowPostModal(true);
    }
    

    return ( 
        <div className='container'>
            <div className="row w-75 mx-auto">
                <AddFeeditem />
            </div>
            <div className="row">
                { feedItems && feedItems.map((item,index)=>(<div key={item.feedItem.feeditem_id} 
                className="post-container mx-auto col-xs-12 col-sm-12 col-md-6 col-lg-4 mt-4">
                    <Feeditem 
                    curr_user_id = {user_id}
                    index={index} 
                    post={item.feedItem} 
                    user={item.user}
                    handleUpvote={handleUpVote}
                    handleDownVote={handleDownVote}
                    isLikedByUser = {item.isLikedByUser}
                    isDislikedByUser = {item.isDislikedByUser}
                    showFullPost = {showFullPost}
                    openCommentModal = {openCommentModal}
                    />
                </div>))}
                {feedItems.length === 0 && <ListGroupComponent heading={"No Post Yet"}/>}
            </div>
             {/* Modal */}
             {(showPostModal || showCommentModal) && <div className="overlay-dim"></div> }
             {showPostModal && 
             <div ref={modalRef} className="modal-container">
                 <Modal closeModal = {closeFullPost} 
                 index = {showPostModalIndex}
                 feedItem = {feedItems[showPostModalIndex].feedItem}
                 openCommentModal = {openCommentModal}
                 />
             </div>
             }

             {/* COmment Modal */}
             {showCommentModal && 
             <div  className="comment-modal-container">
                 <Comments
                    closeCommentModal={closeCommentModal}
                    handleOnCommentAdd={handleOnCommentAdd}
                    curr_user_id={user_id}
                    curr_user_email = {email}
                    index = {showPostModalIndex}
                    comments={feedItems[showPostModalIndex].feedItem.comment}
                    feedItemIndex = {showPostModalIndex}
                    feeditem_id={feedItems[showPostModalIndex].feedItem.feeditem_id}
                 />
             </div>
             }

             
        </div>

            
     );
}
 
export default NewsFeed;