/* This file is part of the db4o object database http://www.db4o.com

Copyright (C) 2004 - 2011  Versant Corporation http://www.versant.com

db4o is free software; you can redistribute it and/or modify it under
the terms of version 3 of the GNU General Public License as published
by the Free Software Foundation.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program.  If not, see http://www.gnu.org/licenses/. */
package com.db4o.db4ounit.jre12.collections;

import java.util.*;

import com.db4o.*;
import com.db4o.config.*;

import db4ounit.*;
import db4ounit.extensions.*;

@decaf.Ignore(decaf.Platform.JDK11)
public class CascadeDeleteCollectionTestCase extends AbstractDb4oTestCase{
	
	public static class Item {
		public List _list;
	}
	
	public class Element{
		public String _name;
		
		public Element(String name){
			_name = name;
		}
	}
	
	@Override
	protected void configure(Configuration config) throws Exception {
		config.objectClass(Item.class).objectField("_list").cascadeOnDelete(true);
	}
	
	public void store(){
		Item item = new Item();
		item._list = new ArrayList();
		item._list.add(new Element("1"));
		store(item);
	}
	
	public void test(){
		Item item = retrieveOnlyInstance(Item.class);
		db().delete(item);
		db().commit();
		ObjectSet objectSet = db().query(Element.class);
		Assert.areEqual(0, objectSet.size());
	}
	
}
