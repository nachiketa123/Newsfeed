import React, { useState } from 'react';
import ListGroupComponent from './ListGroupComponent';
import getClassNames from '../../utils/getClassNames';

const IconCounter = ({
    Icon=()=>{},
    listOfCounterItem=[], 
    reverse=false, 
    cssClass='',
    handleIconClick,
    iconSize,
    limitForLikedUserList=5,
    curr_user_id,
    containerIndex=-1,
    containerId,
}) => {

    const [showListItem, setShowListItem] = useState(false);

    return ( 
        <div style={{minWidth:'35px'}} 
        className='d-flex justify-content-around w-50 align-content-center'>
                {/* showing the users who have upvote the post */}
                <div onMouseEnter={()=>setShowListItem(true)} 
                    onMouseLeave={()=>setShowListItem(false)} 
                    className= {getClassNames({dc:'position-relative',cc:'order-2'},reverse)}>

                    {showListItem && <div style={{bottom:'50px', width:'200px'}} 
                                        className='position-absolute liked-user-list'>
                        <div className="list-group">

                            {listOfCounterItem.length>limitForLikedUserList ?
                                [...listOfCounterItem].splice(0,limitForLikedUserList)
                                .map(u=><ListGroupComponent key={u.id}
                                    extra_text={u.name}/>)  :
                                    
                                    listOfCounterItem.map(u=><ListGroupComponent key={u.id}
                                        extra_text={u.id === curr_user_id ? "You" :u.name}/>)
                                    }
                            {listOfCounterItem.length>limitForLikedUserList && <ListGroupComponent
                                extra_text={"..."}/>}
                        </div>
                    </div>}
                    <span  >{listOfCounterItem.length}</span>
                </div>
                <Icon 
                className={cssClass} 
                onClick={()=>handleIconClick(containerIndex,containerId)} 
                size={iconSize}/>
            </div>
     );
}
 
export default IconCounter;