package com.flipkar.shopping.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.flipkart.shopping.entity.Product;
import com.flipkart.shopping.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	
	EntityManager entityManger;
	
	@Autowired
	public MyDataRestConfig(EntityManager entityManager) {
		// TODO Auto-generated constructor stub
		this.entityManger=entityManager;
	}
	
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		HttpMethod[] thaUnSupportedAction = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
		
		config.getExposureConfiguration()
		.forDomainType(Product.class)
		.withItemExposure((metaData,httpMetods)->httpMetods.disable(thaUnSupportedAction))
		.withCollectionExposure((metaData,httpMetods)->httpMetods.disable(thaUnSupportedAction));
		
		
		config.getExposureConfiguration()
		.forDomainType(ProductCategory.class)
		.withItemExposure((metaData,httpMetods)->httpMetods.disable(thaUnSupportedAction))
		.withCollectionExposure((metaData,httpMetods)->httpMetods.disable(thaUnSupportedAction));
		
		exposeId(config);
	}

	private void exposeId(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
		//Exposing Id in the spring data rest response
		Set<EntityType<?>> entities = entityManger.getMetamodel().getEntities();
		
		List<Class> entityClasses = new ArrayList<>();
		
		for(EntityType entityType:entities) {
			entityClasses.add(entityType.getJavaType());
		}
		
		Class[] domainType = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainType);
		
	}

}
