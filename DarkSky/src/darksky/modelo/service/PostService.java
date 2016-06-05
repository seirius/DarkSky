package darksky.modelo.service;

import java.util.ArrayList;
import java.util.List;

import darksky.exceptions.ExceptionService;
import darksky.modelo.bean.CategoriaPost;
import darksky.modelo.bean.Comentario;
import darksky.modelo.bean.EventoNotificacion;
import darksky.modelo.bean.Imagen;
import darksky.modelo.bean.Post;
import darksky.modelo.bean.Respuesta;
import darksky.modelo.bean.SeguirPost;
import darksky.modelo.bean.SeguirPostId;
import darksky.modelo.bean.VotosPost;
import darksky.modelo.bean.VotosPostId;
import darksky.modelo.dao.CategoriaPostDAO;
import darksky.modelo.dao.ComentarioDAO;
import darksky.modelo.dao.ImagenDAO;
import darksky.modelo.dao.NotificacionDAO;
import darksky.modelo.dao.PostDAO;
import darksky.modelo.dao.SeguirDAO;
import darksky.modelo.dao.VotosPostDAO;
import darksky.util.ServiceManager;
import darksky.util.interfaces.Comment;

public class PostService {
	
	public Post actualizarPost(Post post) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			manager.beginTransaction();
			postDAO.update(post);
			manager.commit();
		} finally {
			manager.close();
		}
		
		return post;
	}
	
	public Post insertarPost(Post post) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		ImagenDAO imagenDAO = manager.getImagenDAO();
		
		try {
			manager.beginTransaction();
			
			Imagen imagen = post.getImagen();
			if (imagen != null) {
				imagenDAO.insert(imagen);
			}
			
			postDAO.insert(post);
			manager.commit();
		} finally {
			manager.close();
		}
		
		return post;
	}
	
	public void eliminarPost(Post post) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		VotosPostDAO votosPostDAO = manager.getVotosPostDAO();
		ImagenDAO imagenDAO = manager.getImagenDAO();
		
		try {
			manager.beginTransaction();
			
			Imagen imagen = post.getImagen();
			
			votosPostDAO.eliminarPorPost(post);
			postDAO.delete(post);
			
			if (imagen != null) {
				imagenDAO.delete(imagen);
			}
			
			manager.commit();
		} finally {
			manager.close();
		}
	}

	public List<Post> getNoticias() {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		List<Post> noticias = new ArrayList<Post>();
		
		try {
			noticias = postDAO.getNoticias();
		} finally {
			manager.close();
		}
		
		
		return noticias;
	}
	
	public Post getPost(int idPost) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			return postDAO.getPost(idPost);
		} finally {
			manager.close();
		}
	}
	
	public List<Post> getPostPorCategoria(CategoriaPost categoria) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		List<Post> categorias = new ArrayList<Post>();
		
		try {
			categorias = postDAO.getPostsPorCategoria(categoria);
		} finally {
			manager.close();
		}
		
		
		return categorias;
	}
	
	public List<Post> getPostPorCategoria(CategoriaPost categoria, int resultado, String orderBy) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		List<Post> categorias = new ArrayList<Post>();
		
		try {
			categorias = postDAO.getPostsPorCategoria(categoria, resultado, orderBy);
		} finally {
			manager.close();
		}
		
		
		return categorias;
	}
	
	public int votar(String usuarioNick, int idPost, String valorVoto) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		VotosPostDAO votosDAO = manager.getVotosPostDAO();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			manager.beginTransaction();
			Post post = postDAO.getPost(idPost);
			VotosPost oldVoto = votosDAO.getVoto(idPost, usuarioNick);
			VotosPost voto = new VotosPost();
			voto.setId(new VotosPostId(usuarioNick, idPost));
			
			if (oldVoto == null) { //Si no habia voto se inserta uno con el valorVoto pasado
				voto.setValorVoto(valorVoto);
				votosDAO.insertar(voto);
			} else if (oldVoto.getValorVoto().equals(valorVoto)) { //Si el valorVoto existente coincide con el nuevo se elimina
				votosDAO.eliminar(oldVoto);
			} else if ( (valorVoto.equals("1")  && oldVoto.getValorVoto().equals("-1") ) ||
					    (valorVoto.equals("-1") && oldVoto.getValorVoto().equals("1")) ) { //Si los valoresVoto son diferentes, se actualizan
				oldVoto.setValorVoto(valorVoto);
				votosDAO.update(oldVoto);
			}
			
			int votosPorPost = votosDAO.getVotosPorPost(post);
			post.setVotos((long) votosPorPost);
			postDAO.update(post);
			
			manager.commit();
			
			return votosPorPost;
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}
	
	public int eliminarVoto(String usuarioNick, int idPost, String valorVoto) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		VotosPostDAO votosDAO = manager.getVotosPostDAO();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			manager.beginTransaction();
			//Get post
			Post post = postDAO.getPost(idPost);
			//Create voto
			VotosPostId votoID = new VotosPostId(usuarioNick, idPost);
			VotosPost voto = new VotosPost();
			voto.setId(votoID);
			voto.setValorVoto(valorVoto);
			//Eliminar Voto
			votosDAO.eliminar(voto);
			//Calcular el numero de votos del post
			int votosPorPost = votosDAO.getVotosPorPost(post);
			post.setVotos((long) votosPorPost);
			//Update post
			postDAO.update(post);
			
			manager.commit();
			
			return votosPorPost;
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}
	
	public void votarPositivo(String usuarioNick, int idPost) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		VotosPostDAO votosDAO = manager.getVotosPostDAO();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			manager.beginTransaction();
			//Get post
			Post post = postDAO.getPost(idPost);
			//Create voto
			VotosPost voto = new VotosPost(new VotosPostId(usuarioNick, idPost), "1");
			//Insertar Voto
			votosDAO.insertar(voto);
			//Calcular el numero de votos del post
			post.setVotos((long) votosDAO.getVotosPorPost(post));
			//Update post
			postDAO.update(post);
			
			manager.commit();
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
		
	}
	
	public void votarNegativo(String usuarioNick, int idPost) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		VotosPostDAO votosDAO = manager.getVotosPostDAO();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			manager.beginTransaction();
			//Get post
			Post post = postDAO.getPost(idPost);
			//Create voto
			VotosPost voto = new VotosPost(new VotosPostId(usuarioNick, idPost), "-1");
			//Insertar Voto
			votosDAO.insertar(voto);
			//Calcular el numero de votos del post
			post.setVotos((long) votosDAO.getVotosPorPost(post));
			//Update post
			postDAO.update(post);
			
			manager.commit();
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
		
	}
	
	public List<CategoriaPost> getCategorias() {
		ServiceManager manager = new ServiceManager();
		CategoriaPostDAO categoriaDAO = manager.getCategoriaPostDAO();
		
		List<CategoriaPost> categorias = null;
		
		try {
			categorias = categoriaDAO.getCategorias();
		} finally {
			manager.close();
		}
		
		return categorias;
	}
	
	public int getVotoPorPost(int idPost, String nick) {
		ServiceManager manager = new ServiceManager();
		VotosPostDAO votosPostDAO = manager.getVotosPostDAO();
		
		int valorVoto = -2;

		try {
			VotosPost voto = votosPostDAO.getVoto(idPost, nick);
			if (voto != null) {
				valorVoto = voto.getIntValor();
			}
			
			return valorVoto;
		} finally {
			manager.close();
		}
	}
	
	public List<Respuesta> getRespuestasPorComentario(Comentario comentario) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			return postDAO.getRespuestasPorComentario(comentario);
		} finally {
			manager.close();
		}
	}
	
	public List<Respuesta> getRespuestasPorRespuesta(Respuesta respuesta) {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			return postDAO.getRespuestasPorRespuesta(respuesta);
		} finally {
			manager.close();
		}
	}
	
	public void comentar(Comentario comentario) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
		
		try {
			manager.beginTransaction();
			comentarioDAO.comentar(comentario);
			
			int idPost = comentario.getPost().getId();
			List<SeguirPost> listaSeguirPost = seguirDAO.getSeguirPostPorPost(idPost);
			for (SeguirPost seguirPost: listaSeguirPost) {
				SeguirPostId id = seguirPost.getId();
				String usuOrigin = id.getUsuOrigin();
				
				if (!usuOrigin.equalsIgnoreCase(comentario.getUsuario().getNick())) {
					notificacionDAO.nuevaNotificacion(id.getUsuOrigin(), EventoNotificacion.POST_NEW_COMENTARIO, String.valueOf(idPost), comentario.getId().toString());
				}
				
			}
			
			manager.commitClose();
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}

	public void responder(Respuesta respuesta) throws ExceptionService {

		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		SeguirDAO seguirDAO = manager.getSeguirDAO();
		NotificacionDAO notificacionDAO = manager.getNotificacionDAO();
		
		try {
			manager.beginTransaction();
			comentarioDAO.responder(respuesta);
			
			int idPost = comentarioDAO.getIdPostFromRespuesta(respuesta);
			List<SeguirPost> listaSeguirPost = seguirDAO.getSeguirPostPorPost(idPost);
			for (SeguirPost seguirPost: listaSeguirPost) {
				SeguirPostId id = seguirPost.getId();
				String usuOrigin = id.getUsuOrigin();
				
				if (!usuOrigin.equalsIgnoreCase(respuesta.getUsuario().getNick())) {
					notificacionDAO.nuevaNotificacion(id.getUsuOrigin(), EventoNotificacion.POST_NEW_RESPUESTA, String.valueOf(idPost), respuesta.getId().toString());
				}
			}
			
			manager.commit();
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}
	
	public List<Comment> getComentarios(Post post) throws ExceptionService {

		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		List<Comment> listComentarios = new ArrayList<Comment>();
		
		try {
			List<Comentario> comms = postDAO.getComentarios(post);
			for (Comentario comentario: comms) {
				comentario.setNivel(0);
				listComentarios.add(comentario);
				manageRespuestas(listComentarios, postDAO, comentario);
			}
			
			return listComentarios;
		} catch(Exception e) {
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}
	
	public List<Comment> getAllRespuestasPorComentario(Comentario comentario) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		List<Comment> listRespuestas = new ArrayList<Comment>();
		
		try {
			List<Respuesta> resps = postDAO.getRespuestasPorComentario(comentario);
			for (Respuesta respuesta: resps) {
				respuesta.setNivel(1);
				listRespuestas.add(respuesta);
				manageRespuestas(listRespuestas, postDAO, (Comment) respuesta);
			}
			
			return listRespuestas;
		} catch(Exception e) {
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}
	
	private void manageRespuestas(List<Comment> listComentarios, PostDAO postDAO, Comment comment) {

		if (comment instanceof Comentario) {
			Comentario comentario = (Comentario) comment;
			List<Respuesta> respuestas = postDAO.getRespuestasPorComentario(comentario);
			for (Respuesta respuesta: respuestas) {
				respuesta.setNivel(1);
				listComentarios.add(respuesta);
				manageRespuestas(listComentarios, postDAO, respuesta);
			}
		} else if (comment instanceof Respuesta) {
			Respuesta respuesta = (Respuesta) comment;
			List<Respuesta> respuestas = postDAO.getRespuestasPorRespuesta(respuesta);
			for (Respuesta res: respuestas) {
				res.setNivel(respuesta.getNivel() + 1);
				listComentarios.add(res);
				manageRespuestas(listComentarios, postDAO, res);
			}
		}
	}
	
	public void visitPost(Post post) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		PostDAO postDAO = manager.getPostDAO();
		
		try {
			manager.beginTransaction();
			postDAO.visitPost(post);
			manager.commit();
		} catch(Exception e) {
			manager.rollback();
			if (e.getCause() == null) {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage());
			} else {
				e.printStackTrace();
				throw new ExceptionService(e.getMessage(), e.getCause());
			}
		} finally {
			manager.close();
		}
	}
	
	public void editarComentario(Comentario comentario) throws ExceptionService {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		
		try {
			manager.beginTransaction();
			comentarioDAO.updateComentario(comentario);
			manager.commit();
		} catch(Exception e) {
			e.printStackTrace();
			throw new ExceptionService("Error interno", e.getCause());
		} finally {
			manager.close();
		}
	}
	
	public void editarRespuesta(Respuesta respuesta) {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		
		try {
			manager.beginTransaction();
			comentarioDAO.updateRespuesta(respuesta);
			manager.commit();
		} finally {
			manager.close();
		}
	}
	
	public Comentario getComentario(int idComentario) {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		
		Comentario comentario = null;
		
		try {
			comentario = comentarioDAO.getComentario(idComentario);
		} finally {
			manager.close();
		}
		
		return comentario;
	}
	
	public Respuesta getRespuesta(int idRespuesta) {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		
		Respuesta respuesta = null;
		
		try {
			respuesta = comentarioDAO.getRespuesta(idRespuesta);
		} finally {
			manager.close();
		}
		
		return respuesta;
	}
	
	public void eliminarComentario(Comentario comentario) {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		
		try {
			manager.beginTransaction();
			comentarioDAO.deleteComentario(comentario);
			manager.commit();
		} finally {
			manager.close();
		}
	}
	
	public void eliminarRespuesta(Respuesta respuesta) {
		ServiceManager manager = new ServiceManager();
		ComentarioDAO comentarioDAO = manager.getComentarioDAO();
		
		try {
			manager.beginTransaction();
			comentarioDAO.deleteRespuesta(respuesta);
			manager.commit();
		} finally {
			manager.close();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
