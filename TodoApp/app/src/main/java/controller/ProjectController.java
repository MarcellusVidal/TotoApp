package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Marcellus Vidal
 */
public class ProjectController {

    private ResultSet resultSet;
    
    public void save(Project project){
       String sql = "INSERT INTO projects (name, description, createdAt, "
               + "updatedAt) VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;   
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto", ex);
        }finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
         
    }

    public void update(Project project){
        String sql = "UPDATE projeto SET name = ?, description = ?,"
                + " createdAt = ?, updatedAt = ?, WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
           //Executando a query
            statement.execute();
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM project";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resulSet = null;
        
        //Lista de tarefas que será removida quando a chamada do método acontecer
        List<Project> projects = new ArrayList<>();
        
        try {
            //Cria conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
           
             //Valor retornado pela execução da query
            resulSet = statement.executeQuery();
            
            //Enquanto houverem valores a serem percorridos no meu resu
            while (resulSet.next()) {
                Project project = new Project();
                
                project.setId(resulSet.getInt("id"));
                project.setName(resulSet.getString("name"));
                project.setDescription(resulSet.getString("description"));
                project.setCreatedAt(resulSet.getDate("createdAt"));
                project.setUpdateAt(resulSet.getDate("updatedAt"));
                
                project.add(project);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir a tarefa", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return projects;
    }
    
    public void removeById(int idProject) {
        
        String sql = "DELETE FROM projects WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            //Setando os valores 
            statement.setInt(1, idProject);
            
            //Executando a query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    
}
