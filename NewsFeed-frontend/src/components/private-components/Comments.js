import React, {useRef} from 'react';
import ListGroupComponent from '../common/ListGroupComponent';
import { AiFillLike ,AiFillDislike } from "react-icons/ai";
import { timeSince } from '../../utils/dateFormat';
import IconCounter from '../common/IconCounter';
import getClassNames from '../../utils/getClassNames';
import { useDispatch } from 'react-redux';
import { upvoteComment, downvoteComment } from '../../store/actions/newsfeed-action';

const limitForLikedUserList = 5;

const Comments = ({
    curr_user_id, 
    curr_user_email,
    feedItemIndex,
    comments, 
    feeditem_id, 
    handleOnCommentAdd, 
    closeCommentModal
}) => {

    //ref
    const commentInputRef = useRef();

    //dispatch
    const dispatch = useDispatch();

    const handleUpvote = (commentIndex,comment_id)=>{
        dispatch(upvoteComment(curr_user_email,feedItemIndex,commentIndex,feeditem_id,comment_id));
    }
    const handleDownvote = (commentIndex,comment_id)=>{
        dispatch(downvoteComment(curr_user_email,feedItemIndex,commentIndex,feeditem_id,comment_id));
    }

    //functions
    const extraTextToUpvoteDownvoteReply = (comment,index)=>{
        const { comment_id,upvote, downvote, isLikedByTheUser} = comment;

        return <div className='d-flex align-items-center mt-2'>
            <div>
                {/* upvote icon + showing the users who have upvote the comment */}
                <IconCounter
                        curr_user_id={curr_user_id}
                        handleIconClick={handleUpvote}
                        iconSize={20}
                        Icon={AiFillLike}
                        cssClass={getClassNames({dc:'btn p-0 me-2', cc:'text-primary'},isLikedByTheUser)}
                        limitForLikedUserList={limitForLikedUserList}
                        listOfCounterItem={upvote}
                        containerId={comment_id}
                        containerIndex={index}
                        reverse={true}
                    />
            </div>
            <div className='ms-4'>
                {/* downvote icon + showing the users who have upvote the comment */}
                <IconCounter
                        curr_user_id={curr_user_id}
                        handleIconClick={handleDownvote}
                        iconSize={20}
                        Icon={AiFillDislike}
                        cssClass={getClassNames({dc:'btn p-0 me-2', cc:'text-danger'},comment.isDislikedByTheUser)}
                        limitForLikedUserList={limitForLikedUserList}
                        listOfCounterItem={downvote}
                        containerId={comment_id}
                        containerIndex={index}
                        reverse={true}
                    />
            </div>
        </div>
    }

    //click handlers
    const onCommentSubmit = (e,commentText,feeditem_id)=>{
        e.preventDefault();
        handleOnCommentAdd(e,commentText,feeditem_id)
        commentInputRef.current.value="";
    }
    return ( 
        <div className="modal fade show d-block" >
                  <div  className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" >Comments</h1>
                        <button onClick={closeCommentModal} type="button" className="btn-close" ></button>
                    </div>
                    <div className="modal-body">
                        <div className="list-group">

                        {(comments && comments.length!==0 ) ? comments.map((comment,index)=>(
                            <ListGroupComponent
                            key={comment.comment_id}
                            heading={comment.user.name}
                            subheading={comment.comment_txt}
                            rightSideText={timeSince(comment.time)}
                            extra_text={extraTextToUpvoteDownvoteReply(comment,index)}
                            />)):
                            
                            <ListGroupComponent
                            subheading={"No comments yet"}
                            />}
                        </div>
                    </div>
                    <div className="modal-footer">
                        <form onSubmit={(e)=>onCommentSubmit(e,commentInputRef.current.value,feeditem_id)} className='w-100 d-flex justify-content-between align-items-center'>
                            <input ref={commentInputRef} name='add_comment' className='form-control flex-grow-1' placeholder='Add New comment'/>
                            <button style={{marginLeft:'25px'}} type='submit' className='btn btn-secondary'>
                                Submit
                            </button>
                        </form>
                    </div>
                    </div>
                </div>
            </div>
     );
}
 
export default Comments;