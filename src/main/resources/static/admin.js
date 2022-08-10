const renderUsers = (users) => {
    let output = ''
    users.forEach(user => {
        output += `
              <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.username}</td>
                    <td>${user.roles.map(role => role.roleName === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td>
              <td>
                   <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                    data-toggle="modal" data-target="modal" id="edit-user" data-id="${user.id}">Edit</button>
              </td>
              <td>
                   <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger"
                   data-toggle="modal" data-target="modal" id="delete-user" data-id="${user.id}">Delete</button>
              </td>
              </tr>`
    })
    info.innerHTML = output;
}

let users = [];
const updateUser = (user) => {
    const foundIndex = users.findIndex(x => x.id === user.id);
    users[foundIndex] = user;
    renderUsers(users);
    console.log('users');
}

const removeUser = (id) => {
    users = users.filter(user => user.id !== id);
    renderUsers(users);
    console.log('users');
}


const info = document.querySelector('#allUsers');
const url = 'http://localhost:8080/api/admin/user'

fetch(url, {mode: 'cors'})
    .then(res => res.json())
    .then(data => {
        users = data;
        renderUsers(data)
    })


const addUserForm = document.querySelector('#addUser')
const addName = document.getElementById('name3')
const addLastName = document.getElementById('lastname3')
const addPassword = document.getElementById('password3')
const addUsername = document.getElementById('username3')
const addRoles = document.getElementById('roles3')

addUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: addName.value,
            lastName: addLastName.value,
            username: addUsername.value,
            password: addPassword.value,
            roles:
                Array.from(addRoles)
                    .filter(option => option.selected)
                    .map(option => option.value)
        })
    })
        .then(res => res.json())
        .then(data => {
            users = data;
            renderUsers(users);
        })
})

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}


on(document, 'click', '#edit-user', e => {
    const userInfo = e.target.parentNode.parentNode
    document.getElementById('id0').value = userInfo.children[0].innerHTML
    document.getElementById('name0').value = userInfo.children[1].innerHTML
    document.getElementById('lastName0').value = userInfo.children[2].innerHTML
    document.getElementById('username0').value = userInfo.children[3].innerHTML
    document.getElementById('password0').value = userInfo.children[4].innerHTML
    document.getElementById('roles0').value = userInfo.children[5].innerHTML

    $("#modalEdit").modal("show")
})

let currentUserIdd = null;
const editUserForm = document.querySelector('#modalEdit')
editUserForm.addEventListener('submit', (e) => {

    e.preventDefault();

    fetch('http://localhost:8080/api/admin/user/' + currentUserIdd, {

        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('id0').value,
            firstName: document.getElementById('name0').value,
            lastName: document.getElementById('lastName0').value,
            username: document.getElementById('username0').value,
            password: document.getElementById('password0').value,
            roles:
                Array.from(document.getElementById("roles0"))
                    .filter(option => option.selected)
                    .map(option => option.value)

        })
    })
        .then(res => res.json()).then(data => updateUser(data))
        .catch((e) => console.error(e))
    $("#modalEdit").modal("hide")
})


let currentUserId = null;
const deleteUserForm = document.querySelector('#modalDelete')
deleteUserForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch('http://localhost:8080/api/admin/user/' + currentUserId, {
        method: 'DELETE'
    })
        .then(res => res.json())
        .then(data => {
            removeUser(currentUserId);
            deleteUserForm.removeEventListener('submit', () => {
            });
            users = data;
            renderUsers(users);
        }).catch((e) => console.error(e))
    $("#modalDelete").modal("hide")
})

on(document, 'click', '#delete-user', e => {
    const fila2 = e.target.parentNode.parentNode
    currentUserId = document.getElementById('id2').value = fila2.children[0].innerHTML
    document.getElementById('name2').value = fila2.children[1].innerHTML
    document.getElementById('lastName2').value = fila2.children[2].innerHTML
    document.getElementById('username2').value = fila2.children[3].innerHTML
    document.getElementById('roles2').value = fila2.children[4].innerHTML

    $("#modalDelete").modal("show")
})


const url3 = 'http://localhost:8080/api/user'
let loggedUserHeaderElem = document.querySelector('#navBarAdmin')

fetch(url3)
    .then(res => res.json())
    .then(data => {
        loggedUserHeaderElem.innerHTML = `
                <b th:utext=>${data.username}</b>
                <lable>with roles:</lable>
                <lable th:text=>${data.roles.map(role => role.roleName === 'ROLE_USER' ? 'USER' : 'ADMIN')}</lable>`;
    })