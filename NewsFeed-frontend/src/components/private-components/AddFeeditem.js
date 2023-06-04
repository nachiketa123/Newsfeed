import React,{ useRef } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { addFeedItem } from '../../store/actions/newsfeed-action';

const AddFeeditem = () => {
    const contentRef = useRef();
    const titleRef = useRef();

    const dispatch = useDispatch();
    const { email } = useSelector(state=>state.user)

    const handleSubmitPost = (e) =>{
        e.preventDefault();
        const content = contentRef.current.value;
        const title = titleRef.current.value;
        dispatch(addFeedItem(email,title,content));
        contentRef.current.value = '';
        titleRef.current.value = '';
    }

    return ( 
    <form action='submit' onSubmit={handleSubmitPost} className='d-flex flex-column add-feeditem mt-3'>
        <input required={true} ref={titleRef} name='title' className='form-control' placeholder='Title of your post'/>
        <textarea required={true} ref={contentRef} name='postContent' className='form-control text-area mt-3' placeholder='write your post here'/>
        <button type='submit' className='btn btn-primary mt-2 align-self-center'>Post</button>
    </form> );
}
 
export default AddFeeditem;