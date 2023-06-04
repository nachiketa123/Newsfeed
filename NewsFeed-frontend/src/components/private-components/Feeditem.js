import React from 'react';
import { timeSince } from '../../utils/dateFormat';
import { AiFillLike ,AiFillDislike } from "react-icons/ai";
import { FaComment } from "react-icons/fa";
import getClassNames from '../../utils/getClassNames';
import IconCounter from '../common/IconCounter';

const limitForLikedUserList = 5;

const Feeditem = ({curr_user_id, 
    user, 
    post, 
    index, 
    handleUpvote, 
    handleDownVote, 
    isLikedByUser, 
    isDislikedByUser,
    showFullPost,
    openCommentModal,
}) => {

    //destructuring props
    const { name:username } = user;
    const { feeditem_id,title,content,upvote,downvote,time } = post;


    return ( 
        <div className="card">
            <div data-bs-toggle="tooltip" data-bs-placement="top" data-bs-title="Click to see full post" data-modal-trigger="true" onClick={()=>showFullPost(index)} className="card-header text-capitalize">
                {username}
            </div>
            <div onClick={()=>showFullPost(index)} className="card-body">
                <h5 className="card-title text-capitalize">{title}</h5>
                <div className='custom-truncate'>
                    <p className="card-text truncate-text text-capitalize">{content}</p>
                </div>
                
            </div>
            <div className="card-footer text-body-secondary d-flex justify-content-between align-items-center">
                <span>
                        {timeSince(time)}
                </span> 
                <div className="upvote-downvote-container w-25 d-flex justify-content-between">
                   
                   {/* showing the users who have upvote the post */}
                    <IconCounter
                        curr_user_id={curr_user_id}
                        handleIconClick={handleUpvote}
                        iconSize={20}
                        Icon={AiFillLike}
                        cssClass={getClassNames({dc:'ms-1 me-1 mt-1',cc:'text-primary'},isLikedByUser)}
                        limitForLikedUserList={limitForLikedUserList}
                        listOfCounterItem={upvote}
                        containerId={feeditem_id}
                        containerIndex={index}
                    />

                    {/* showing the users who have downvote the post */}
                    <IconCounter
                        curr_user_id={curr_user_id}
                        handleIconClick={handleDownVote}
                        iconSize={20}
                        Icon={AiFillDislike}
                        cssClass={getClassNames({dc:'ms-1 mt-1 me-1', cc:'text-danger'},isDislikedByUser)}
                        limitForLikedUserList={limitForLikedUserList}
                        listOfCounterItem={downvote}
                        containerId={feeditem_id}
                        containerIndex={index}
                        reverse={true}
                    />
                </div>
                <div onClick={()=>openCommentModal(index)} className="comment-container">
                    <FaComment size={20}/>
                </div>
            </div>
        </div>
     );
}
 
export default Feeditem;