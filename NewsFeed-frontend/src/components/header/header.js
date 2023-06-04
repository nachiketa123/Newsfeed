import './header.css';

import React,{ useContext, useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import UserAuthContext from '../../application-context/UserAuthContext';
import ListGroupComponent from '../common/ListGroupComponent';
import { RiUserFollowFill, RiUserUnfollowFill } from "react-icons/ri";
import { useSelector, useDispatch } from 'react-redux';
import { searchUser, followUser, unfollowUser } from '../../store/actions/user-follow-action';
import { userFollowActions } from '../../store/user-follow-slice';

const Header = () => {
    //context
    const { isAuthenticated, logout } = useContext(UserAuthContext);

    //refs
    const searchUserRef = useRef();
    const searchUserDivRef = useRef();

    //dispatch
    const dispatch = useDispatch();
    
    //selectors - redux state
    const { searchResult: userSearchResult } = useSelector(state=>state.userFollow)
    const { email } = useSelector(state=> state.user)

    //states
    const [showSearchResult,setShowSearchResult] = useState(false);

    //side effects
    useEffect(()=>{
        const handleClickOutsideSearchResult = (e) =>{
            if(searchUserDivRef.current && !searchUserDivRef.current.contains(e.target)){
                setShowSearchResult(false);
                dispatch(userFollowActions.setSearchResult([]))
            }
        }

        document.addEventListener('click',handleClickOutsideSearchResult);

        return ()=>{
            document.removeEventListener('click',handleClickOutsideSearchResult);
        }
    },[dispatch])

    const handleSearchUser = (e) =>{
        e.preventDefault();
        const searchText = searchUserRef.current.value
        dispatch(searchUser(email,searchText))
        setShowSearchResult(true);
    }

    const handleUserFollow = (userToBeFollowed) =>{
        dispatch(followUser(email, userToBeFollowed))
    }

    const handleUserUnFollow = (userToBeUnFollowed) =>{
        dispatch(unfollowUser(email, userToBeUnFollowed))
    }

    const getFollowButtonHTML = (email, isFollowed) =>{

        return (
            !isFollowed? 
            <div style={{minWidth:'72.09px'}} 
            className='d-flex flex-column align-items-center btn btn-primary px-1'
            onClick={e=>handleUserFollow(email)}> 
                <RiUserFollowFill color='white' size={20}/>
                <small  color='white'>Follow</small>
            </div>
                
            : <div style={{minWidth:'72.09px'}} 
            className='d-flex flex-column align-items-center btn btn-danger px-1'
            onClick={e=>handleUserUnFollow(email)}>
                <RiUserUnfollowFill color='white' size={20} />
                <small color='white'>UnFollow</small>
            </div> 
        )
    }
    return ( 
        <div className='header'>
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="/">NewsFeed</Link>
                    <button className="navbar-toggler order-sm-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNavDropdown">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <Link className="nav-link active" aria-current="page" to="#">Home</Link>
                            </li>
                            {!isAuthenticated && 
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">Login</Link>
                            </li>}
                            {isAuthenticated && 
                            <li className="nav-item">
                                <button className="nav-link" onClick={logout} >Logout</button>
                            </li>}
                            {/* <li className="nav-item">
                                <a className="nav-link" href="#">Pricing</a>
                            </li> */}
                            {/* <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Dropdown link
                                </a>
                                <ul className="dropdown-menu">
                                    <li><a className="dropdown-item" href="#">Action</a></li>
                                    <li><a className="dropdown-item" href="#">Another action</a></li>
                                    <li><a className="dropdown-item" href="#">Something else here</a></li>
                                </ul>
                            </li> */}
                        </ul>
                    </div>
                    {isAuthenticated && <form onSubmit={handleSearchUser} className="search-user-form order-sm-2 d-flex " role="search">
                        <input autoComplete='off' ref={searchUserRef} name='search_user_text' className="form-control me-2" type="search" placeholder="Search" aria-label="Search"/>
                        <button className="btn btn-outline-success" type="submit">Search</button>
                    </form>}
                </div>
            </nav>

            {showSearchResult && <div className="overlay-dim"></div> }
            {showSearchResult && <div ref={searchUserDivRef} className="user-list-group-container">
                            <div className="list-group">

                                {userSearchResult.length!==0 ? userSearchResult.map(user=>(
                                    <ListGroupComponent 
                                    heading={user.name}
                                    subheading={user.email}
                                    rightSideText={getFollowButtonHTML(user.email,user.isFollowedByCurrentUser)}
                                    key = {user.email}
                                    
                                    />
                                    )):
                                    <ListGroupComponent heading={"No user found"} subheading={"try some other name"}/>
                                }
                                </div>
                </div>}
        </div>
     );
}
 
export default Header;