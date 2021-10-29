let addButton = document.getElementById('add');
let todoContainer = document.getElementById('todo_Container');
let inputField = document.getElementById('input1');

addButton.addEventListener('click', function(){
    var paragraph = document.createElement('p');
    paragraph.classList.add('paragraph-styling');
    paragraph.innerText = input1.value;
    todoContainer.appendChild(paragraph);
    input1.value = "";
    paragraph.addEventListener('click', function(){
        paragraph.style.textDecoration = "line-through";
    })
    paragraph.addEventListener('dblclick', function(){
        todoContainer.removeChild(paragraph);
    })
})