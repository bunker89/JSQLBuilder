package com.bunker.jsqlbuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.bunker.jsqlbuilder.binder.Binder;
import com.bunker.jsqlbuilder.setters.IntSetter;
import com.bunker.jsqlbuilder.setters.PreparedSetter;

public class SelectQueryBuilder extends PreparedQueryBuilder {
	private String table, read, name, last;
	private List<JoinQueryBuilder> joinList = new LinkedList<>();
	private List<PreparedPair> terms = new LinkedList<>();
	private List<Binder> binders = new LinkedList<>();
	
	public SelectQueryBuilder(String table, @Nullable String read, @Nullable String name, Binder...readBinders) {
		this.table = table;
		this.read = read;
		this.name = name;
		
		for (Binder binder : readBinders) {
			binders.add(binder);
			
			if (this.read != null && !this.read.equals("")) {
				this.read += "," + binder.getFieldName();
			} else {
				this.read = binder.getFieldName();
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
		if (termPair.setters != null)
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
				if (joinSelect.query != null && !joinSelect.query.equals("")) {
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
			if (joinSelect.query != null && !joinSelect.query.equals("")) {
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
		jQuery.insertRead("j.*");
		jQuery.insertEqualOn("j.2", "a", "b");
		jQuery.insertOn("6?7?", new IntSetter(6), new IntSetter(7));
		JoinQueryBuilder jQuery2 = new JoinQueryBuilder("join", "j_table", "j");	
		jQuery2.insertRead("j.*");
		jQuery2.insertRead("j.*");
		jQuery2.insertEqualOn("j.2", "a", "b");
		jQuery2.insertOn("6?7?", new IntSetter(6), new IntSetter(7));
		SelectQueryBuilder selectQuery = new SelectQueryBuilder("s_table", "s.*", "s");
		selectQuery.insertLast("last");
		selectQuery.insertJoin(jQuery);
		selectQuery.insertJoin(jQuery2);
		PreparedPair pair = selectQuery.build(); 
		System.out.println(pair.query);
		System.out.println(pair.setters);
		System.out.println(selectQuery.binders);
	}
}