import { useState } from "react";
import { NavLink } from "react-router";

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <header className="relative z-5 bg-white">
      <div className="mx-auto w-[calc(100%-80px)] max-w-330 max-[760px]:w-[min(calc(100%-36px),560px)] max-[420px]:w-[calc(100%-32px)] flex h-22 items-center gap-9 max-[1050px]:gap-5 max-[760px]:h-17.5">
        <NavLink
          className="inline-flex shrink-0 items-center gap-2.75 text-[21px] font-extrabold tracking-[-.04em] whitespace-nowrap text-blue-deep no-underline max-[760px]:text-[19px]"
          to="/#dau-trang"
          aria-label="CarePath, về đầu trang"
        >
          <span
            className="grid size-8.75 place-items-center rounded-[11px_11px_11px_3px] bg-blue text-white [&_svg]:size-5.25"
            aria-hidden="true"
          >
            <svg viewBox="0 0 24 24" fill="none">
              <path
                d="M12 3v18M3 12h18"
                stroke="currentColor"
                strokeWidth="3"
                strokeLinecap="square"
              />
            </svg>
          </span>
          CarePath
        </NavLink>
        <nav
          className="ml-auto flex items-center gap-8.25 text-[13px] font-semibold max-[1050px]:gap-4.5 max-[760px]:absolute max-[760px]:top-17.5 max-[760px]:right-0 max-[760px]:left-0 max-[760px]:m-0 max-[760px]:hidden max-[760px]:flex-col max-[760px]:items-stretch max-[760px]:gap-0 max-[760px]:bg-white max-[760px]:p-4.5 max-[760px]:shadow-[0_18px_25px_rgba(0,0,0,.12)] max-[760px]:data-[open=true]:flex [&_a]:whitespace-nowrap [&_a]:no-underline [&_a]:transition-colors [&_a]:duration-200 [&_a:hover]:text-blue max-[760px]:[&_a]:px-1.25 max-[760px]:[&_a]:py-3"
          id="primary-nav"
          aria-label="Điều hướng chính"
          data-open={isMenuOpen}
        >
          <NavLink to="/#ve-nen-tang" onClick={() => setIsMenuOpen(false)}>Về CarePath</NavLink>
          <NavLink to="/#cach-dat-lich" onClick={() => setIsMenuOpen(false)}>Lựa chọn tìm kiếm</NavLink>
          <NavLink to="/#khung-gio" onClick={() => setIsMenuOpen(false)}>Lịch tiếp nhận</NavLink>
          <NavLink to="/#danh-cho-benh-vien" onClick={() => {
            setIsMenuOpen(false);
            document.getElementById("danh-cho-benh-vien")?.scrollIntoView();
          }}>Dành cho bệnh viện</NavLink>
        </nav>
        <NavLink
          className="inline-flex items-center gap-3 border border-blue px-4.25 py-2.75 text-[13px] font-bold whitespace-nowrap text-blue no-underline transition-colors duration-200 hover:bg-blue hover:text-white max-[760px]:hidden [&_svg]:size-3.75"
          to="/#tim-kiem"
        >
          Đăng nhập{" "}
          <svg viewBox="0 0 20 20" fill="none" aria-hidden="true">
            <path
              d="M3 10h13m-5-5 5 5-5 5"
              stroke="currentColor"
              strokeWidth="1.7"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
        </NavLink>
        <button
          className="ml-auto hidden border-0 bg-transparent p-2 text-blue-deep max-[760px]:block"
          id="menu-button"
          type="button"
          aria-expanded={isMenuOpen}
          aria-controls="primary-nav"
          aria-label={isMenuOpen ? "Đóng menu" : "Mở menu"}
          onClick={() => setIsMenuOpen((open) => !open)}
        >
          <svg
            width="25"
            height="25"
            viewBox="0 0 25 25"
            fill="none"
            aria-hidden="true"
          >
            <path
              d="M3 7h19M3 13h19M3 19h19"
              stroke="currentColor"
              strokeWidth="2"
            />
          </svg>
        </button>
      </div>
    </header>
  );
};

export default Header;
