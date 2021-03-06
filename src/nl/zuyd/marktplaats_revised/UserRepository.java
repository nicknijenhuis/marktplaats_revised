package nl.zuyd.marktplaats_revised;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Singleton
public class UserRepository implements IRepository<User> 
{
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User getById(int id)
	{
		return this.em.find(User.class, id);
	}

	@Override
	public List<User> getAll()
	{
		TypedQuery<User> q = this.em.createQuery("SELECT c FROM User c", User.class);
		return q.getResultList();
	}
}