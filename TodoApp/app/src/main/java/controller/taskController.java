package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;


/**
 *
 * @author Marcellus Vidal
 */
public class taskController {

    public taskController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void save(Task task){
        String sql = "INSERT INTO tasks (idProject, name, description, completed,"
                + " notes, deadline, createdAt, updatedAt) VALUES (?, ?, ?, ?," 
                        + "?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;   
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.execute();            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        }
    
    public void update(Task task){
        String sql = "UPDATE tasks SET idProject = ?, name = ?, description = ?,"
                + " completed = ?, notes = ?, deadline = ?, createdAt = ?,"
                + " updatedAt = ?, WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores 
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.setInt(9, task.getId());
           //Executando a query
            statement.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + ex.getMessage(), ex);
        }
    }

    public void removeById(int TaskId) throws SQLException{
        String sql = "DELETE FROM tasks WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores 
            statement.setInt(1, TaskId);
            
            //Executando a query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject){
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resulSet = null;
        
        //Lista de tarefas que será removida quando a chamada do método acontecer
        List<Task> tasks = new ArrayList<>();
        
        try {
            //Cria conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
             //Valor retornado pela execução da query
            resulSet = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu resu
            while (resulSet.next()) {
                Task task = new Task();
                task.setId(resulSet.getInt("id"));
                task.setIdProject(resulSet.getInt("idProject"));
                task.setName(resulSet.getString("name"));
                task.setDescription(resulSet.getString("description"));
                task.setNotes(resulSet.getString("notes"));
                task.setIsCompleted(resulSet.getBoolean("completed"));
                task.setDeadline(resulSet.getDate("deadline"));
                task.setCreatedAt(resulSet.getDate("createdAt"));
                task.setUpdateAt(resulSet.getDate("updatedAt"));
                
                task.add(task);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        return tasks;
    }
}
