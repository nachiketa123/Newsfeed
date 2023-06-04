
const fetchWithAuth = async (uri, options={headers:{}}) =>{
    let token = localStorage.getItem('token');
    token = 'Bearer ' + token;

    let {headers} = options;

    headers = {...headers, Authorization : token}

    return await fetch(uri,{...options,headers});      
}

export default fetchWithAuth;