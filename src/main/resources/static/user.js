function fillInPrincipal() {
    fetch("/api/v1/user/authUser")
        .then(res => res.json())
        .then(data => {
            $('#headerUserInfo').append(data.email)
                .append(' with roles: ')
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#headerUserInfo').append(roles);
            let user = `$(
                <tr>
                    <td>${data.id}</td>
                    <td>${data.firstName}</td>
                    <td>${data.lastName}</td>
                    <td>${data.age}</td>
                    <td>${data.email}</td>   
                    <td>${roles}</td>
                </tr>)`;
            $('#userInformationTable').append(user);
        })
}

fillInPrincipal();
