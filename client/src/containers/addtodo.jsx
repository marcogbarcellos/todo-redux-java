import React from 'react'
import { connect } from 'react-redux'
import { addTodo } from '../actions/index.jsx'

let AddTodo = ({ dispatch }) => {
  let inputText, inputPriority

  return (
    <div>
      <form className="todo-form" onSubmit={e => {
        e.preventDefault()
        if (!inputText.value.trim()) {
          return
        }
        dispatch(addTodo(inputText.value, inputPriority.value))
        inputText.value = ''
        inputPriority.value = ''
      }}>
        <label>Text:</label>
        <input type="text" ref={node => {
          inputText = node
        }} />
        <label>Priority:</label>
        <input type="number" style={{width: 30 + 'px'}}  ref={node => {
          inputPriority = node
        }} />
        <button type="submit">
          Add Todo
        </button>
      </form>
    </div>
  )
}
AddTodo = connect()(AddTodo)

export default AddTodo
