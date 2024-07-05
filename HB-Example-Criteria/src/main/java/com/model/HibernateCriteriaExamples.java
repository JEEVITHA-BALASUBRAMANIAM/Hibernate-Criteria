package com.model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;

public class HibernateCriteriaExamples{
@SuppressWarnings("unchecked")
public static void main(String args[]) {
	SessionFactory sessionFactory=HBUtil.getSessionFactory();
	Session session =sessionFactory.getCurrentSession();
	Transaction tx=session.beginTransaction();
	Criteria criteria=session.createCriteria(Employee.class);
	List <Employee> empList=criteria.list();
	for(Employee emp:empList) {
		System.out.println("ID="+emp.getId()+" "+emp.getName()+" "+emp.getSalary());
	}
	criteria =session.createCriteria(Employee.class).add(Restrictions.eq("id",new Integer(3)));
	Employee emp=(Employee)criteria.uniqueResult();
	System.out.println("Name="+emp.getName());
	//paging
	empList=session.createCriteria(Employee.class).addOrder(Order.desc("id")).setFirstResult(0).setMaxResults(2).list();
	for(Employee emp4:empList) {
		System.out.println("paginated employees::"+emp4.getId()+emp4.getName());}
		//like exp
		empList =session.createCriteria (Employee.class)
				.add(Restrictions.like("name","%m%"))
			.list();
		for(Employee emp5:empList) {
			System.out.println("Employee is having 'm' in name::"+emp5.getName());
			
		}
		//projection  eg
	Criteria c = session.createCriteria(Employee.class);
	c.setProjection(Projections.rowCount());
		empList=c.add(Restrictions.like("name","%m%")).list();
		System.out.println("Number of Employees with'm' in name ="+empList.getFirst());
		//Using projection for sum,mn,max Aggregated 
		Criteria d= session.createCriteria (Employee.class);
		empList=d.setProjection(Projections.sum("salary")).list();
		System.out.println("sum of salaries="+empList.getFirst());
		//Rollback transaction to avoid 
		tx.commit();
		//closing hibernate
		sessionFactory.close();
		
	}
}