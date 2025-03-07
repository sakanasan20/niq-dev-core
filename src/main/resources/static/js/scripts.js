$(document).ready(function() {
	// Sidebar Toggle 按鈕控制
	$("#toggleSidebar").click(function() {
		$("#sidebar").toggleClass("collapsed");

		// 檢查 Sidebar 是否收縮，來隱藏/顯示子菜單圖示
		if ($("#sidebar").hasClass("collapsed")) {
			$("#submenuToggleIcon").hide(); // Sidebar 縮小時，隱藏折疊圖示
			$("#submenu").collapse('hide');  // 假設所有的子菜單都有 ".submenu" 類別
		} else {
			$("#submenuToggleIcon").show(); // Sidebar 展開時，顯示折疊圖示
		}
	});

	// 點擊 "設定" 選單，展開或收起子菜單
	$("#collapseSetting").click(function() {
		// 切換 Chevron 圖示 (只有在 Sidebar 展開時才變動)
		if (!$("#sidebar").hasClass("collapsed")) {
			$("#submenu").collapse('toggle');
			
			if ($("#submenuToggleIcon").hasClass("bi-chevron-down")) {
				$("#submenuToggleIcon").removeClass("bi-chevron-down").addClass("bi-chevron-up");
			} else {
				$("#submenuToggleIcon").removeClass("bi-chevron-up").addClass("bi-chevron-down");
			}
		}
	});

	// 當按下登出按鈕時顯示登出確認 Modal
	$("#logoutBtn").click(function() {
		$('#logoutModal').modal('show');
	});
	
	// 監聽公告下拉選單變更
	$("#categorySelect").on("change", function() {
	    var selectedCategory = $(this).val(); // 取得選中的類別
	
	    if (selectedCategory === "all") {
	        $(".announcement-card").show(); // 顯示所有公告
	    } else {
	        $(".announcement-card").hide(); // 隱藏所有公告
	        $(".announcement-card[data-category='" + selectedCategory + "']").show(); // 只顯示選中的類別
	    }
	});

});