<div class="row margin-top-2">
	<div class="col-xs-12">
		<div id="capaNotificaciones">
			<div id="slideNotificacion1">
				<div class="row">
					<div class="col-xs-12">
						<button class="btn btn-warning btn-block notification-slide-down" id="abrirNotificaciones" disabled>No hay notificaciones nuevas</button>
					</div>
				</div>
			</div>
			<div id="slideNotificacion2">
				<ul class="nav nav-pills nav-justified">
					<li class="active"><a data-toggle="pill" href="#notisUsuario" id="pill_usuarios">Usuarios</a></li>
					<li><a data-toggle="pill" href="#notisPost" id="pill_entradas">Entradas</a></li>
				</ul>
				<div class="tab-content">
					<div id="notisUsuario" class="tab-pane fade in active"></div>
					<div id="notisPost" class="tab-pane fade">
						<ul id="listaNotificacionesPost" class='list-group margin-top-1'>
							<button class='list-group-item mostrarMasNotificaciones'>
								<div class='text-center'>Mostrar todas las notificaciones</div>
							</button>
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<button class="btn btn-warning btn-block notification-slide-up" id="cerrarNotificaciones" disabled>No hay notificaciones nuevas</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

