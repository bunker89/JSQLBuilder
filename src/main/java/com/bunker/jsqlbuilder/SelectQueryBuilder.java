package com.bunker.jsqlbuilder;

import java.util.LinkedList;
import java.util.List;

public class SelectQueryBuilder {
	private String table, read, name, last;
	private List<JoinQueryBuilder> joinList = new LinkedList<>();
	private List<String> terms = new LinkedList<>();

	public SelectQueryBuilder(String table, String read, String name) {
		this.table = table;
		this.read = read;
		this.name = name;
	}

	public SelectQueryBuilder insertTerm(String term) {
		this.terms.add(term);
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

	public String build() {
		String query = "select " + read + " ";

		//읽어드릴 필드들 정의
		for (JoinQueryBuilder j : joinList) {
			String read = j.getRead();
			if (read != null && !read.equals("")) {
				query += "," + j.getRead() + " ";
			}
		}

		//테이블 과 별명 정의
		query += "from " + table;		
		if (name != null && !name.equals("")) {
			query += "as " + name;
		}

		//조인들 적용
		for (JoinQueryBuilder j : joinList) {
			String joinQuery = j.getJoinQuery();
			if (joinQuery != null && !joinQuery.equals("")) {
				query += joinQuery;
			}
		}
		
		//검색 조건들 적용
		query += createTerms();
		
		if (last != null)
			query += last;
		return query;
	}
	
	private String createTerms() {
		String query = "";
		boolean hasTerm = false;
		//if select term size is bigger then 0 than hasTerm is true. and if join builder has term more than one that is true also.
		if (terms.size() <= 0) {
			for (JoinQueryBuilder j : joinList) {
				String joinSelect = j.getTerm();
				if (joinSelect != null && !joinSelect.equals("")) {
					hasTerm = true;
					break;
				}
			}
		} else {
			hasTerm = true;
		}
		
		if (!hasTerm)
			return query;

		query += " where ";

		if (terms.size() > 0) {
			query += terms.remove(0) + " ";
		}
		for (String s : terms) {
			query += "and " + s + " ";
		}
		for (JoinQueryBuilder j : joinList) {
			String joinSelect = j.getTerm();
			if (joinSelect != null && !joinSelect.equals("")) {
				query += "and " + joinSelect + " ";
			}
		}
		return query;
	}
	
	public static void main(String []args) {
		JoinQueryBuilder jQuery = new JoinQueryBuilder("join", "j_table", "j");
		jQuery.insertRead("j.*");
		jQuery.insertTerm("j.abc=s.abc");
		jQuery.insertTerm("j.bc=s.bc");
		jQuery.insertOn("j.1");
		
		SelectQueryBuilder selectQuery = new SelectQueryBuilder("s_table", "s.*", "s");
		selectQuery.insertTerm("a=3 and b=4");
		selectQuery.insertTerm("c=5");
		selectQuery.insertLast("last");
		selectQuery.insertJoin(jQuery);
		selectQuery.insertJoin(jQuery);
		System.out.println(selectQuery.build());
	}
}