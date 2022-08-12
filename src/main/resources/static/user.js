const url = 'http://localhost:8080/api/user'
const header = document.querySelector('#head')
const user = document.querySelector('#User')

fetch(url)
    .then(res => res.json())
    .then(data => {
        console.log(data)
        header.innerHTML = `
                <b th:utext=>${data.username}</b>
                <lable>with roles:</lable>
                <lable th:text=>${data.roles.map(role => role.roleName)}</lable>`;
        user.innerHTML = `
                                <td>${data.id}</td>
                                <td>${data.firstName}</td>
                                <td>${data.lastName}</td>
                                <td>${data.roles.map(role => role.roleName)}</td>
                                `;
    })