package com.tracker.client.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;

public class HistoryHelper {

    /**
     * This will parse "p1=4&p2=5" into a HashMap<p1, 4> ... etc
     * 
     * @todo implement "p1=4&p2=5" parsing
     * @param param
     * @return
     */
    static public HashMap<String, String> unserializeParams(String serialized) {
        HashMap<String, String> res = new HashMap<String, String>();

        if (serialized == null || serialized.isEmpty()) {
            return res;
        }

        String ss = serialized;

        if (ss.contains("?")) {
            // remove base url
            ss = serialized.replace(HistoryHelper.getBaseUrl(serialized), "");
            ss = ss.substring(1, ss.length());
        }

        //Log.debug("HistoryHelper:deserialize("+ss+")");

        // return empty HashMap
        if (!ss.contains("&") && !ss.contains("=")) {
            return res;
        }

        String[] params = ss.split("&");
        for (String p : params) {
            String[] kv = p.split("=");
            String key = kv[0];
            String value = (kv.length > 1) ? kv[1] : "";
            // urldecode
            value = URL.decodeQueryString(value);
            res.put(key, value);
        }

        return res;
    }

    /**
     * Return only params from url ( removed baseUrl )
     * @param serialized
     * @return
     */
    static public String getUrlParams(String serialized) {
        String ss = serialized;

        if (ss.contains("?")) {
            // remove base url
            ss = serialized.replace(HistoryHelper.getBaseUrl(serialized), "");
            ss = ss.substring(1, ss.length());
        }

        return ss;

    }

    /**
     * method to get base url for current token
     * 
     * @return contact/list
     */
    static public String getBaseUrl() {
        return getBaseUrl(History.getToken());
    }

    /**
     * Returns base url from given url.
     * 
     * @param serialized        contacts/list?p=1&p=2
     * @return                  contacts/list
     */
    static public String getBaseUrl(String url) {
        if (url == null || url.isEmpty())
            return "";
        if (url.contains("?")) {
            String tmp[] = url.split("\\?");
            if (tmp.length > 2) {
                return tmp[0] + "?" + tmp[1];
            }
            return tmp[0];
        }

        if (url.contains("=")) {
            return "";
        }

        return url;
    }

    /**
     * This will generate a string to use as token Parameter (i.e.
     * #contact_list?p4=4&fg=5) etc.
     *
     * EMPTY VALUED parameters ARE EXCLUDED from result
     * @param params
     * @return
     */
    static public String serializeParams(Map<String, String> params) {
        String res = "";
        for (String key : params.keySet())
            if (!key.isEmpty() && !params.get(key).isEmpty()) {
                res = res + key + "=" + URL.encodeQueryString(params.get(key)) + "&";
            }

        // remove trailing amp
        if (res.endsWith("&")) {
            res = res.substring(0, res.length()-1);
        }

        return res;
    }

    /**
     * Example input: ?sort=first_name&dir=asc
     * 
     * Output: ?sort=first_name&dir=new_dir, if (dir == 0), toggles sorting
     * direction: ?sort=first_name&dir=desc,
     * 
     * @return
     */
    static public String getSortFieldToken(String fieldName, String dir) {
        String token = History.getToken();
        HashMap<String, String> params = HistoryHelper.unserializeParams(token);

        String d = dir;

        if (params.containsKey("sort") && params.get("sort").equals(fieldName)
                && params.containsKey("dir")) {
            // toggle direction
            String currentDir = params.get("dir");
            d = currentDir.equals("asc") ? "desc" : "asc";
        }

        params.put("sort", fieldName);
        params.put("dir", d);

        return HistoryHelper.getBaseUrl(token) + "?"
        + HistoryHelper.serializeParams(params);
    }

    /**
     * Assigns default "ASC" sorting
     * 
     * @param fieldName
     * @return
     */
    static public String getSortFieldToken(String fieldName) {
        return HistoryHelper.getSortFieldToken(fieldName, "desc");
    }

    /**
     * Returns 'asc'/'desc' depending on current History token If there is no
     * such field, return empty string
     * 
     * @param fieldName
     * @return
     */
    static public String getSortFieldDirection(String fieldName) {
        String token = History.getToken();
        HashMap<String, String> params = HistoryHelper.unserializeParams(token);

        if (params.containsKey("sort")) {

            String sortField = params.get("sort");
            if (sortField.equals(fieldName)) {
                return params.containsKey("dir") ? params.get("dir") : "";
            }

        } else {
            return "";
        }

        return "";
    }

    /**
     * Return parameter value or null if there are no parameter
     * @param parameter
     * @return
     */
    static public String getUrlParameter(String parameter) {
        HashMap<String, String> url = HistoryHelper.unserializeParams(History.getToken());
        if (url.containsKey(parameter)) {
            return url.get(parameter);
        }

        return null;
    }

    /**
     * method to set one additional param to current token
     * @param key
     * @param value
     * @return String
     */
    static public String addParamToCurrentToken(final String key, final String value) {
        return HistoryHelper.addParamsToCurrentToken(new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }

    /**
     * method to set additional params to current token
     * @param params
     * @return String
     */
    static public String addParamsToCurrentToken(HashMap<String,String> params) {

        String res = History.getToken();

        HashMap<String,String> curParams = HistoryHelper.unserializeParams(res);

        //if empty params, just return current token
        if (params.isEmpty()) {
            return res;
        }

        curParams.putAll(params);

        res = getBaseUrl(res) + "?";

        for (Entry<String,String> entry : curParams.entrySet()) {
            res += entry.getKey() + "=" + StringHelper.urlEncode(entry.getValue()) + "&";
        }

        res = res.substring(0, res.length() - 1); // remove last '&'

        return res;
    }

    /**
     * Removes params from current token by params keys
     * @param Arraylist params keys of params to be removed
     * @return
     */
    static public String removeParamsFromCurrentToken(ArrayList<String> params) {
        String res = History.getToken();

        HashMap<String,String> curParams = HistoryHelper.unserializeParams(res);

        //if empty params, just return current token
        if (params.isEmpty()) {
            return res;
        }

        for (String string : params) {
            if (curParams.containsKey(string)) {
                curParams.remove(string);
            }
        }

        res = getBaseUrl(res) + "?";

        for (Entry<String,String> entry : curParams.entrySet()) {
            res += entry.getKey() + "=" + StringHelper.urlEncode(entry.getValue()) + "&";
        }

        res = res.substring(0, res.length() - 1); // remove last '&'

        return res;
    }

    /**
     * helpful method in order to don't redirect to token if another module already loaded
     * example: create contact, quick press Activities ...
     *          and we don't need to redirect to contact profile from Activities when contact's creating callback returned
     * @param itemToLoad
     */
    static public void newItemWithModuleCheck(String itemToLoad) {
        String currentModuleToken = History.getToken();
        if (currentModuleToken.contains("/")) {
            currentModuleToken = "app/" + History.getToken().split("/")[1];
        }
        if (itemToLoad.startsWith(currentModuleToken)) {
            History.newItem(itemToLoad);
        }
    }
}
