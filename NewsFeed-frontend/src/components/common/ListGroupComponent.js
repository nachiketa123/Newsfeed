import React from 'react';

const ListGroupComponent = ({heading, subheading, extra_text, rightSideText}) => {
    return ( 
        // <div className="list-group">
            <div  className="list-group-item list-group-item-action bg-body-light d-flex justify-content-between" aria-current="true">
                <div className="d-flex flex-column flex-grow-1 justify-content-between">
                    {heading && <h5 className="mb-1 text-capitalize">{heading}</h5>}
                    {subheading && <p className="mb-1">{subheading}</p>}
                    {extra_text && <small>{extra_text}</small>}
                </div>
                <div >
                    {rightSideText && <small>{rightSideText}</small>}
                </div>
            </div>
        // </div>
     );
}
 
export default ListGroupComponent;