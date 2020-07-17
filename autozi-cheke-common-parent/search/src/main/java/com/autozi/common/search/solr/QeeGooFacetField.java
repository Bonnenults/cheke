package com.autozi.common.search.solr;

import java.util.List;

/**
 * 类描述:
 * 创建人: yourun.liu
 * 创建时间: 13-4-25 下午1:39
 */
public class QeeGooFacetField {
    private String name;
    private List<Count> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Count> getList() {
        return list;
    }

    public void setList(List<Count> list) {
        this.list = list;
    }

    public static class Count implements java.io.Serializable,Comparable<Count>{
        private String name;//如果QeeGooFacetField里的name是brand的话，这里的count就是brand的Id
        private long count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

		public int compareTo(Count o) {
			if (Integer.parseInt(name) != Integer.parseInt(o.name)) {
				return Integer.parseInt(name) - Integer.parseInt(o.name);
			}
			return 0;
		}
    }
}
