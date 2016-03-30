package com.cct.evernoteclient.Models;

/**
 * Created by Carlos Carrasco Torres on 30/03/2016.
 */
public class Filter {

    private FilterBuilder.FilterParameters filterParams;
    private FilterBuilder.FilterOrder filterOrder;

    public FilterBuilder.FilterOrder getFilterOrder() {
        return filterOrder;
    }

    public void setFilterOrder(FilterBuilder.FilterOrder filterOrder) {
        this.filterOrder = filterOrder;
    }

    public FilterBuilder.FilterParameters getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(FilterBuilder.FilterParameters filterParams) {
        this.filterParams = filterParams;
    }

    public static class FilterBuilder {

        private FilterParameters filterParametersBuilder = FilterParameters.CREATION;
        private FilterOrder filterOrderBuilder = FilterOrder.ASCENDING;

        public enum FilterParameters {
            TITLE, CREATION
        }

        public enum FilterOrder {
            ASCENDING, DESCENDING
        }

        public Filter.FilterBuilder setParameters(FilterParameters filterParameters) {
            this.filterParametersBuilder = filterParameters;
            return this;
        }

        public Filter.FilterBuilder setOrder(FilterOrder filterOrder) {
            this.filterOrderBuilder = filterOrder;
            return this;
        }

        public Filter createFilter() {
            Filter filter = new Filter();
            filter.setFilterParams(filterParametersBuilder);
            filter.setFilterOrder(filterOrderBuilder);
            return filter;
        }

    }
}
