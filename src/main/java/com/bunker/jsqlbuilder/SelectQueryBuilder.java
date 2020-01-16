package com.bunker.jsqlbuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.bunker.jsqlbuilder.binder.Binder;
import com.bunker.jsqlbuilder.binder.IntBinder;
import com.bunker.jsqlbuilder.setters.IntSetter;
import com.bunker.jsqlbuilder.setters.PreparedSetter;

public class SelectQueryBuilder extends PreparedQueryBuilder {
	private String table, read, name, last;
	private List<JoinQueryBuilder> joinList = new LinkedList<>();
	private List<PreparedPair> terms = new LinkedList<>();
	private List<Binder> binders = new LinkedList<>();
	
	@SafeVarargs
	public SelectQueryBuilder(String table, @Nullable String read, @Nullable String name, Binder...readBinders) {
		this.table = table;
		this.read = read;
		this.name = name;
		
		for (Binder binder : readBinders) {
			binders.add(binder);
			
			if (this.read != null && !this.read.equals("")) {
				this.read += "," + binder.getFieldName();
			} else {
				this.read += binder.getFieldName();
			}
		}
	}
	
	public SelectQueryBuilder insertTerm(String term, PreparedSetter...setters) {
		this.terms.add(new PreparedPair(term, Arrays.asList(setters)));
		return this;
	}
	
	public SelectQueryBuilder insertJoin(JoinQueryBuilder joinBuilder) {
		joinList.add(joinBuilder);
		return this;
	}
	
	public SelectQueryBuilder insertLast(String last) {
		this.last = last;
		return this;
	}

	@Override
	public PreparedPair build() {
		String query = "select " + (read != null ? read + " " : "1 ");
		List<PreparedSetter> newSetters = new LinkedList<>();

		//읽어드릴 필드들 정의
		for (JoinQueryBuilder j : joinList) {
			String read = j.getRead();
			binders.addAll(j.getBinders());
			if (read != null && !read.equals("")) {
				query += "," + read + " ";
			}
		}

		//테이블 과 별명 정의
		query += "from " + table;		
		if (name != null && !name.equals("")) {
			query += " as " + name;
		}

		//조인들 적용
		for (JoinQueryBuilder j : joinList) {
			PreparedPair joinQuery = j.getJoinQuery();
			if (joinQuery.query != null && !joinQuery.query.equals("")) {
				query += joinQuery.query;
				newSetters.addAll(joinQuery.setters);
			}
		}
		
		//검색 조건들 적용
		PreparedPair termPair = createTerms();
		query += termPair.query;
		newSetters.addAll(termPair.setters);
		
		if (last != null)
			query +=  " " + last;
		return new PreparedPair(query, newSetters);
	}
	
	private PreparedPair createTerms() {
		String query = "";
		boolean hasTerm = false;
		//if select term size is bigger then 0 than hasTerm is true. and if join builder has term more than one that is true also.
		if (terms.size() <= 0) {
			for (JoinQueryBuilder j : joinList) {
				PreparedPair joinSelect = j.getTerm();
				if (joinSelect != null && !joinSelect.query.equals("")) {
					hasTerm = true;
					break;
				}
			}
		} else {
			hasTerm = true;
		}
		
		if (!hasTerm)
			return new PreparedPair(query, null);
		
		List<PreparedSetter> newSetters = new LinkedList<>();

		query += " where";

		if (terms.size() > 0) {
			PreparedPair pair = terms.remove(0); 
			query += " " + pair.query;
			newSetters.addAll(pair.setters);
		}
		
		for (PreparedPair s : terms) {
			query += " and " + s.query;
			newSetters.addAll(s.setters);
		}
		
		for (JoinQueryBuilder j : joinList) {
			PreparedPair joinSelect = j.getTerm();
			if (joinSelect != null && !joinSelect.equals("")) {
				query += " and " + joinSelect.query;
				newSetters.addAll(joinSelect.setters);
			}
		}
		return new PreparedPair(query, newSetters);
	}
	
	public List<Binder> getBinders() {
		return binders;
	}
	
	public static void main(String []args) {
		JoinQueryBuilder jQuery = new JoinQueryBuilder("join", "j_table", "j");	
		jQuery.insertRead("j.*");
		jQuery.insertRead("j.*", new IntBinder("field1"), new IntBinder("field2"));
		jQuery.insertTerm("2?", new IntSetter(2));
		jQuery.insertTerm("1?", new IntSetter(1));
		jQuery.insertOn("6?7?", new IntSetter(6), new IntSetter(7));
		jQuery.insertOn("j.2");
		SelectQueryBuilder selectQuery = new SelectQueryBuilder("s_table", "s.*", "s", new IntBinder("field3"), new IntBinder("field4"));
		selectQuery.insertTerm("4?5?", new IntSetter(4), new IntSetter(5));
		selectQuery.insertTerm("3?", new IntSetter(3));
		selectQuery.insertLast("last");
		selectQuery.insertJoin(jQuery);
		PreparedPair pair = selectQuery.build(); 
		System.out.println(pair.query);
		System.out.println(pair.setters);
		System.out.println(selectQuery.binders);
	}
}