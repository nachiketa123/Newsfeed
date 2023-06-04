import React from 'react';
import { timeSince } from '../../utils/dateFormat';

const Modal = ({closeModal, feedItem, openCommentModal,index}) => {
    
    // destructuring
    const {title, content, time, upvote, downvote} = feedItem;
    return ( 
        <div className="modal fade show d-block z-2" >
                  <div  className="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" >{title}</h1>
                        <button onClick={closeModal} type="button" className="btn-close" ></button>
                    </div>
                    <div className="modal-body">
                        {content}
                    </div>
                    <div className="modal-footer d-flex justify-content-between align-items-center">
                        <div className="d-flex flex-column justify-content-around">
                            <span className='text-secondary'>
                                {timeSince(time)}
                            </span>
                            <span className='text-primary'>No of upvote: {upvote.length}</span>
                            <span className='text-danger'>No of downvote: {downvote.length}</span>
                        </div>
                        <button onClick={()=>openCommentModal(index)} className='btn btn-secondary'>
                            Comments
                        </button>
                    </div>
                    </div>
                </div>
            </div>
     );
}
 
export default React.memo(Modal);