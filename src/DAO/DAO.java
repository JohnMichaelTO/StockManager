package DAO;

public interface DAO<T>
{
	// Select
	//public abstract T select(T obj);
	
	// Insert
	public abstract boolean insert(T obj);
	
	// Update
	public abstract boolean update(T obj);
	
	// Delete
	public abstract boolean delete(T obj);
}