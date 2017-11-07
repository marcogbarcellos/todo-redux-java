import React, { PropTypes } from 'react'

const Todo = ({ onClick, deleteTodo, updateTodo, todo }) => (
    <li>
        <span
            style={{
                fontWeight: 'bold',
                textDecoration: todo.completed? 'line-through': 'none'
            }}
            onClick={onClick}>{todo.text}</span>
        <span
            style={{
                textDecoration: todo.completed? 'line-through': 'none'
            }}
            onClick={onClick}>{' (priority:' + todo.priority + ')' }</span>
        <a href="#" style={{
                marginLeft: "10px",
                textDecoration: todo.completed? 'line-through': 'none'
            }}
            onClick={e => {
                e.preventDefault()
                todo.completed = !todo.completed
                updateTodo()
            }}> - complete </a>

        <a href="#" style={{
                marginLeft: "10px"
            }}
            onClick={e => {
                e.preventDefault()
                deleteTodo()
            }}>(delete)</a>
    </li>
)

Todo.propTypes = {
  onClick: PropTypes.func.isRequired,
  deleteTodo: PropTypes.func.isRequired,
  updateTodo: PropTypes.func.isRequired,
  todo: PropTypes.object.isRequired
}

export default Todo
