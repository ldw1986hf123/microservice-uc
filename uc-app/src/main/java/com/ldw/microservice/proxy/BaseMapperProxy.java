//package com.ldw.microservice.proxy;
//
//import com.ldw.microservice.annotation.Table;
//import org.apache.ibatis.session.SqlSession;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.util.StringJoiner;
//
//public class BaseMapperProxy<T> implements InvocationHandler {
//    private final SqlSession sqlSession;
//    private final Class<T> mapperInterface;
//    private final Class<?> modelClass;
//
//    public BaseMapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Class<?> modelClass) {
//        this.sqlSession = sqlSession;
//        this.mapperInterface = mapperInterface;
//        this.modelClass = modelClass;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if ("insert".equals(method.getName())) {
//            Object entity = args[0];
//            StringBuilder sql = new StringBuilder();
//            String table = modelClass.getSimpleName().toLowerCase();
//
//            if (modelClass.isAnnotationPresent(Table.class)) {
//                table = modelClass.getAnnotation(Table.class).value();
//            }
//
//            Field[] fields = modelClass.getDeclaredFields();
//            StringJoiner columns = new StringJoiner(",");
//            StringJoiner values = new StringJoiner(",");
//
//            for (Field field : fields) {
//                field.setAccessible(true);
//                Object val = field.get(entity);
//                if (val != null) {
//                    columns.add(field.getName());
//                    values.add("#{" + field.getName() + "}");
//                }
//            }
//
//            sql.append("INSERT INTO ").append(table)
//                    .append(" (").append(columns).append(") ")
//                    .append("VALUES (").append(values).append(")");
//
//            return sqlSession.insert("dynamicInsert", entity); // Mapper.xml 里配置一个万能插入点
//        }
//
//        throw new UnsupportedOperationException("未实现方法: " + method.getName());
//    }
//}
