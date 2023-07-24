const urlUsers = "/api/v1/admin/user";

const renderTable = (users) => {
    let result = ''
    for (const user of users) {
        let roles = ''
        user.roles.forEach(role => {
            roles += role.name.replace("ROLE_", "") + '\n'
        })
        result +=
            `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${roles}</td>
                    <td>
                        <button class="btn btn-primary " data-bs-toogle="modal"
                        data-bs-target="#modalEdit"
                        onclick="editModalData(${user.id})">Edit</button>
                    </td>
                    <td>
                        <button class="btn btn-danger btn-sm" data-bs-toogle="modal"
                        data-bs-target="#modalDelete"
                        onclick="deleteModalData(${user.id})">Delete</button>        
                    </td>
                </tr>`
    }
    document.getElementById("allUsersTableBody").innerHTML = result
}

const allRoles = fetch("/api/v1/admin/user/roles")
    .then(response => response.json())

const fillRoles = function (elementId) {
    allRoles.then(roles => {
        let result = ''
        for (const i in roles) {
            result += `<option value = ${roles[i].id}>${roles[i].name.replace("ROLE_", "")}</option>`
        }
        document.getElementById(elementId).innerHTML = result
    })
}

function addPost() {
    const userFormNew = document.getElementById("newUserForm")
    let role = document.getElementById('rolesNewUser')
    let rolesAddUser = []
    let rolesAddUserValue = ''
    for (const i in role.options) {
        if (role.options[i].selected) {
            rolesAddUser.push({id: role.options[i].value, name: 'ROLE_' + role.options[i].innerHTML})
            rolesAddUserValue += role.options[i].innerHTML + ' '
        }
    }
    const user = {
        firstName: document.getElementById('firstNameNewUser').value,
        lastName: document.getElementById('lastNameNewUser').value,
        age: document.getElementById('ageNewUser').value,
        email: document.getElementById('emailNewUser').value,
        password: document.getElementById('passwordNewUser').value,
        roles: rolesAddUser
    }

    fetch(urlUsers, {
        method: 'post',
        body: JSON.stringify(user),
        headers: {
            'Content-type': 'application/json',
        },
    })
        .then(res => {
            return res.json()
        })
        .then(data => {
            console.log(data)
            fetch(urlUsers)
                .then(res => res.json())
                .then(data => renderTable(data))
            userFormNew.reset()
            $('[href="#nav-users-table"]').tab('show');
        })
}

fetch(urlUsers)
    .then(res => res.json())
    .then(data => renderTable(data))


fillRoles("rolesNewUser")

const modalEditClose = document.getElementById('closeEditModalWindow')

modalEditClose.addEventListener('click', () => {
    $('#modalEdit').modal('hide');
})
const modalDeleteClose = document.getElementById("closeDeleteModal")
modalDeleteClose.addEventListener('click', () => {
    $('#deleteModal').modal('hide');
})


const userFormNew = document.getElementById("newUserForm")
userFormNew.addEventListener('submit', function (e) {
    console.log('click submit')
    e.preventDefault();
    addPost();
})


let formEdit = document.forms["formEdit"];
editModal();

async function findUser(id) {
    let url = "/api/v1/admin/user/" + id;
    let response = await fetch(url);
    return await response.json();
}

async function editModalData(id) {
    const modal = new bootstrap.Modal(document.querySelector('#modalEdit'));

    modal.show();
    let user = await findUser(id);
    formEdit.idEdit.value = user.id;
    formEdit.firstNameEdit.value = user.firstName
    formEdit.lastNameEdit.value = user.lastName;
    formEdit.ageEdit.value = user.age;
    formEdit.emailEdit.value = user.email;
    fillRoles('rolesEdit')
}

function editModal() {
    formEdit.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (const i in formEdit.rolesEdit.options) {
            if (formEdit.rolesEdit.options[i].selected) editUserRoles.push({
                id: formEdit.rolesEdit.value,
                name: "ROLE_" + formEdit.rolesEdit.options[i].text
            });
        }
        fetch("/api/v1/admin/user/" + formEdit.idEdit.value, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formEdit.idEdit.value,
                firstName: formEdit.firstNameEdit.value,
                lastName: formEdit.lastNameEdit.value,
                password: formEdit.passwordEdit.value,
                email: formEdit.emailEdit.value,
                age: formEdit.ageEdit.value,
                roles: editUserRoles
            })
        }).then(() => {
            $('#closeEditModalWindow').click();
            fetch(urlUsers)
                .then(res => res.json())
                .then(data => renderTable(data))
        });
    });
}

let formDelete = document.forms["formDelete"];

async function deleteModalData(id) {
    const modal = new bootstrap.Modal(document.querySelector('#modalDelete'));
    modal.show()

    let user = await findUser(id);
    formDelete.idDelete.value = user.id;
    formDelete.firstNameDelete.value = user.firstName
    formDelete.lastNameDelete.value = user.lastName;
    formDelete.ageDelete.value = user.age;
    formDelete.emailDelete.value = user.email;

    let result = '';
    for (const i in user.roles) {
        result += `<option value = ${user.roles[i].id}>${user.roles[i].name.replace("ROLE_", "")}</option>`
    }
    document.getElementById('rolesDelete').innerHTML = result
}

function deleteModal() {
    formDelete.addEventListener("submit", ev => {
        ev.preventDefault();
        fetch("/api/v1/admin/user/" + formDelete.idDelete.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#closeDeleteModal').click();
            fetch(urlUsers)
                .then(res => res.json())
                .then(data => renderTable(data))
        });
    });
}

deleteModal();


